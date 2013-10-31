package auth

import com.feth.play.module.pa.exceptions.AuthException
import com.feth.play.module.pa.user.AuthUser

case class InviteRequiredException(user: AuthUser) extends AuthException {

	def authUser: String = user.getProvider + "/" + user.getId

}