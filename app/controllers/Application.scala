package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import com.feth.play.module.pa.user.AuthUser
import com.feth.play.module.pa.PlayAuthenticate
import auth.UserService
import play.api.cache.Cache
import play.modules.reactivemongo.MongoController
import models.users.InviteCode

object Application extends Controller with Authentication with MongoController  {

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

	def redeemInvite = Action { implicit request =>
		InviteCode.redeemForm.bindFromRequest.fold(
			formWithErrors => { Redirect(routes.Application.signin).flashing("error" -> "foobar") },
			formData => {
				val authUser = Cache.getAs[AuthUser]("pendingInvite_" + formData.authUserId).get
				PlayAuthenticate.getUserService.asInstanceOf[UserService].save(authUser, formData.code)
				Redirect(com.feth.play.module.pa.controllers.routes.Authenticate.authenticate(formData.providerId))
			}
		)
	}

}