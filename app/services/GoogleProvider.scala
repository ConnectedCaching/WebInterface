package services

import play.api.libs.ws.WS
import play.api.{Application, Logger}
import play.api.libs.json.JsObject
import securesocial.core._

/*
 * Copy of the original provider with hotfixes for wrong field names
 */
class GoogleProvider(application: Application) extends OAuth2Provider(application) {
	val UserInfoApi = "https://www.googleapis.com/oauth2/v1/userinfo?access_token="
	val Error = "error"
	val Message = "message"
	val Type = "type"
	val Id = "id" // was authId
	val Name = "name"
	val GivenName = "given_name"
	val FamilyName = "family_name"
	val Picture = "picture"
	val Email = "email"


	override def ssId = GoogleProvider.Google

	def fillProfile(user: SocialUser): SocialUser = {
		val accessToken = user.oAuth2Info.get.accessToken
		val promise = WS.url(UserInfoApi + accessToken).get()

		try {
			val response = awaitResult(promise)
			val me = response.json
			(me \ Error).asOpt[JsObject] match {
				case Some(error) =>
					val message = (error \ Message).as[String]
					val errorType = ( error \ Type).as[String]
					Logger.error("[securesocial] error retrieving profile information from Google. Error type = %s, message = %s"
						.format(errorType,message))
					throw new AuthenticationException()
				case _ =>
					val userId = (me \ Id).as[String]
					val firstName = (me \ GivenName).asOpt[String]
					val lastName = (me \ FamilyName).asOpt[String]
					val fullName = (me \ Name).asOpt[String]
					val avatarUrl = ( me \ Picture).asOpt[String]
					val email = ( me \ Email).asOpt[String]
					user.copy(
						userIdFromProvider = UserIdFromProvider(userId, ssId),
						firstName = firstName.getOrElse(""),
						lastName = lastName.getOrElse(""),
						fullName = fullName.getOrElse(""),
						avatarUrl = avatarUrl,
						email = email
					)
			}
		} catch {
			case e: Exception => {
				Logger.error( "[securesocial] error retrieving profile information from Google", e)
				throw new AuthenticationException()
			}
		}
	}
}

object GoogleProvider {
	val Google = "google"
}