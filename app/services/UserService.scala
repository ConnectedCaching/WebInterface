package services

import play.api.{Logger, Application}
import securesocial.core._
import securesocial.core.providers.Token


class UserService(application: Application) extends UserServicePlugin(application) {

	private var users = Map[String, Identity]()

	def find(id: UserIdFromProvider): Option[Identity] = {
		if ( Logger.isDebugEnabled ) {
			Logger.debug("users = %s".format(users))
		}
		users.get(id.providerId + id.authId)
	}

	def save(user: Identity): Identity = {
		users = users + (user.userIdFromProvider.providerId + user.userIdFromProvider.authId -> user)
		user
	}

	/* only required for UsernamePasswordProvider */
	def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = { None }
	def save(token: Token) {}
	def findToken(token: String): Option[Token] = { None }
	def deleteToken(uuid: String) {}
	def deleteTokens() {}
	def deleteExpiredTokens() {}

}