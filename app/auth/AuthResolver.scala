package auth

import com.feth.play.module.pa.PlayAuthenticate.Resolver
import play.mvc.Call
import com.feth.play.module.pa.exceptions.{AccessDeniedException, AuthException}

class AuthResolver extends Resolver {

	override def login(): Call = {
		controllers.routes.Application.signin
	}

	override def afterAuth(): Call = {
		// The user will be redirected to this page after authentication
		// if no original URL was saved
		controllers.routes.Application.index
	}

	override def auth(provider: String): Call = {
		com.feth.play.module.pa.controllers.routes.Authenticate.authenticate(provider);
	}

	override def askMerge(): Call = {
		null
	}

	override def askLink(): Call = {
		null
	}

	override def afterLogout(): Call = {
		controllers.routes.Application.index
	}

	override def onException(e: AuthException): Call = {

		if (e.isInstanceOf[AccessDeniedException]) {
			return controllers.routes.Application.oAuthDenied(e.asInstanceOf[AccessDeniedException].getProviderKey)
		}

		if (e.isInstanceOf[InviteRequiredException]) {
			val user = e.asInstanceOf[InviteRequiredException].user
			return controllers.routes.Application.inviteRequired(user.getProvider, user.getId)
		}

		return super.onException(e)
	}

}