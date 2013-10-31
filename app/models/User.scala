package models

import com.feth.play.module.pa.user.AuthUserIdentity

case class User(id: String) {

}

object User {

	def findByAuthUserIdentity(identity: AuthUserIdentity): Option[User] = {
		Some(User(identity.getProvider + "/" + identity.getId))
	}

}