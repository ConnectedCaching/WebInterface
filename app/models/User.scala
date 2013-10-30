package models

import com.feth.play.module.pa.user.AuthUserIdentity

case class User(id: String) {

}

object User {

	def findByAuthUserIdentity(identity: AuthUserIdentity): User = {
		User(identity.getProvider + "/" + identity.getId)
	}

}