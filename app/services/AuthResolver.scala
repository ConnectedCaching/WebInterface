package services

import com.feth.play.module.pa.PlayAuthenticate.Resolver
import play.mvc.Call

class AuthResolver extends Resolver {

	override def login(): Call = {
		controllers.routes.Application.signin
	}

	def afterAuth(): Call = {
		// The user will be redirected to this page after authentication
		// if no original URL was saved
		controllers.routes.Application.index
	}

	def auth(provider: String): Call = {
		com.feth.play.module.pa.controllers.routes.Authenticate.authenticate(provider);
	}

	def askMerge(): Call = {
		null
	}

	def askLink(): Call = {
		null
	}

	def afterLogout(): Call = {
		controllers.routes.Application.index
	}

}