package auth.providers

import com.feth.play.module.pa.providers.oauth1.{OAuth1AuthInfo, OAuth1AuthProvider}
import play.api.libs.oauth.{OAuthCalculator, RequestToken}
import com.fasterxml.jackson.databind.JsonNode
import play.mvc.Http.Request
import com.feth.play.module.pa.controllers.Authenticate
import com.feth.play.module.pa.exceptions.{AuthException, AccessDeniedException}
import auth.CcAuthUser

class OkapiProvider(app: play.Application) extends OAuth1AuthProvider[CcAuthUser, OAuth1AuthInfo](app) {

	override def getKey: String = "okapi-de"

	def buildInfo(token: RequestToken): OAuth1AuthInfo = new OAuth1AuthInfo(token.token, token.secret)

	private def readAuthUserFrom(json: JsonNode): CcAuthUser = {
		val id = json.get("uuid").asText
		val displayName = json.get("username").asText
		CcAuthUser(id, getKey, Some(displayName), None)
	}

	/*
	 * This allows custom implementations to enrich an AuthUser object or
	 * provide their own implementation
	 */
	def transform(info: OAuth1AuthInfo): CcAuthUser = {
		val userInfoUrl: String = getConfiguration.getString("userInfoUrl")
		val op: OAuthCalculator = getOAuthCalculator(info)
		val userJson: JsonNode = signedOauthGet(userInfoUrl, op)
		readAuthUserFrom(userJson)
	}

	/*
	 * OKAPI uses different query parameter key for indicating errors during authentication ("error"
	 * instead of the default "oauth_problem" defined by my superclass
	 */
	override def checkError(request: Request): Unit = {
		val error: String = Authenticate.getQueryString(request, "error");
		if (error != null) {
			if (error.equals("access_denied")) {
				throw new AccessDeniedException(getKey());
			} else {
				throw new AuthException(error);
			}
		}
	}

}