package auth

import play.api.Play.current
import com.feth.play.module.pa.service.UserServicePlugin
import com.feth.play.module.pa.user.{AuthUserIdentity, AuthUser}
import models.User
import play.api.cache.Cache
import scala.concurrent.duration._

class UserService(app: play.Application) extends UserServicePlugin(app) {

	private var users = Map[String, AuthUser]()

	override def save(authUser: AuthUser): AnyRef = {
		val key = "pendingInvite_" + authUser.getProvider + "/" + authUser.getId
		Cache.set(key, authUser, 15 minutes)
		throw new InviteRequiredException(authUser)
	}

	def save(user: AuthUser, inviteCode: String): AnyRef = {
		users = users + ((user.getProvider + "/" + user.getId) -> user)
		user
	}

	// due to the shitty java based framework implementation we have to return null here to
	// trigger the execution of the save method
	override def getLocalIdentity(identity: AuthUserIdentity): Option[User] = {
		User.findByAuthUserIdentity(identity)
		return null
	}

	override def merge(newUser: AuthUser, oldUser: AuthUser): AuthUser = ???

	override def link(oldUser: AuthUser, newUser: AuthUser): AuthUser = ???

}