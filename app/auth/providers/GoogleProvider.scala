package auth.providers

import com.feth.play.module.pa.providers.oauth2.OAuth2AuthProvider
import com.feth.play.module.pa.providers.oauth2.google.{GoogleAuthInfo}
import auth.CcAuthUser
import java.lang.String
import com.feth.play.module.pa.exceptions.{AccessTokenException, AuthException}
import play.Logger
import play.api.libs.ws.WS
import scala.concurrent.duration._
import play.api.libs.json.{JsString, JsObject}
import play.libs.WS.Response
import com.fasterxml.jackson.databind.JsonNode
import scala.concurrent.Await

class GoogleProvider(app: play.Application) extends OAuth2AuthProvider[CcAuthUser, GoogleAuthInfo](app) {

	override def getKey = "google"

	private def readAuthUserFrom(json: JsObject): CcAuthUser = {
		val id = (json \ "id").as[String]
		val displayName = (json \ "name").asOpt[String]
		val email = (json \ "email").asOpt[String]
		CcAuthUser(id, getKey, displayName, email)
	}

	protected def transform(info: GoogleAuthInfo, state: String): CcAuthUser = {
		val url = getConfiguration.getString("userInfoUrl")
		val request = WS.url(url).withQueryString((OAuth2AuthProvider.Constants.ACCESS_TOKEN, info.getAccessToken)).get
		val response = Await.result(request, 10 seconds)

		response.json \ OAuth2AuthProvider.Constants.ERROR match {
			case JsString(error) => throw new AuthException(error)
			case _ => return readAuthUserFrom(response.json.asInstanceOf[JsObject])
		}
	}

	def buildInfo(r: Response): GoogleAuthInfo = {
		val n: JsonNode = r.asJson
		Logger.debug(n.toString)
		if (n.get(OAuth2AuthProvider.Constants.ERROR) != null) {
			throw new AccessTokenException(n.get(OAuth2AuthProvider.Constants.ERROR).asText)
		}
		else {
			return new GoogleAuthInfo(n)
		}
	}

}