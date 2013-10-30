package services

import com.feth.play.module.pa.service.UserServicePlugin
import com.feth.play.module.pa.user.{AuthUserIdentity, AuthUser}
import models.User

class UserService(app: play.Application) extends UserServicePlugin(app) {

	private var users = Map[String, AuthUser]()

	def save(authUser: AuthUser): AnyRef = {
		users = users + ((authUser.getProvider + "/" + authUser.getId) -> authUser)
		authUser
	}

	def getLocalIdentity(identity: AuthUserIdentity): User = {
		User.findByAuthUserIdentity(identity)
	}

	def merge(newUser: AuthUser, oldUser: AuthUser): AuthUser = ???

	def link(oldUser: AuthUser, newUser: AuthUser): AuthUser = ???

}