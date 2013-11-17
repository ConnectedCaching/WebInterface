package auth

import com.feth.play.module.pa.user.{AuthUser, AuthUserIdentity}

case class CcAuthUser(
	id: String,
	provider: String,
	displayName: Option[String],
	email: Option[String])

extends AuthUser with  AuthUserIdentity {

	def getId: String = id
	def getProvider: String = provider

}