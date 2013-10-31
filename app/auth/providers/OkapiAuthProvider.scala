package auth.providers

import com.feth.play.module.pa.providers.oauth1.{BasicOAuth1AuthUser, OAuth1AuthInfo, OAuth1AuthProvider}
import play.api.libs.oauth.{OAuthCalculator, RequestToken}
import com.fasterxml.jackson.databind.JsonNode
import play.mvc.Http.Request
import com.feth.play.module.pa.controllers.Authenticate
import com.feth.play.module.pa.exceptions.{AuthException, AccessDeniedException}

class OkapiAuthUser(id: String, info: OAuth1AuthInfo, state: String) extends BasicOAuth1AuthUser(id, info, state) {

	def getName: String = ""

	def getProvider: String = "okapi-de"

}

object OkapiAuthUser {

	def from(json: JsonNode, info: OkapiAuthInfo): OkapiAuthUser = {
		val user = new OkapiAuthUser(json.get("uuid").asText(), info, null)
		user
	}

}

class OkapiAuthInfo(token: String, tokenSecret: String) extends OAuth1AuthInfo(token, tokenSecret) {}

class OkapiAuthProvider(app: play.Application) extends OAuth1AuthProvider[OkapiAuthUser, OkapiAuthInfo](app) {

	override def getKey: String = "okapi-de"

	def buildInfo(token: RequestToken): OkapiAuthInfo = new OkapiAuthInfo(token.token, token.secret)

	/*
	 * This allows custom implementations to enrich an AuthUser object or
	 * provide their own implementation
	 */
	def transform(info: OkapiAuthInfo): OkapiAuthUser = {
		val userInfoUrl: String = getConfiguration.getString("userInfoUrl")
		val op: OAuthCalculator = getOAuthCalculator(info)
		val userJson: JsonNode = signedOauthGet(userInfoUrl, op)

		return OkapiAuthUser.from(userJson, info)
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