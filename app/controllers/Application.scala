package controllers

import play.api._
import play.api.mvc._
import com.feth.play.module.pa.user.AuthUser
import com.feth.play.module.pa.PlayAuthenticate
import auth.UserService

object Application extends Controller with Authentication  {

	def index = Action { implicit request =>
		Ok(views.html.index(currentUser))
	}

	def signin = Action { implicit request =>
		Ok(views.html.authentication.signin())
	}

	def webInterface = Authenticated { implicit user => implicit request =>
		Ok(views.html.webInterface(user))
	}

	def oAuthDenied(provider: String) = Action {
		Redirect(routes.Application.signin).flashing(
			"error" -> "You need to accept the OAuth connection in order to use this website!"
		)
	}

	def inviteRequired(providerId: String, userId: String) = Action { implicit request =>
		Ok(views.html.authentication.activateInvite(providerId, userId))
	}

	def activateAccount(inviteCode: String) = Action { implicit request =>
		PlayAuthenticate.getUserService.asInstanceOf[UserService].save(null, inviteCode)
		Ok("")
	}

}