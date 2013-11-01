package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import com.feth.play.module.pa.user.AuthUser
import com.feth.play.module.pa.PlayAuthenticate
import auth.UserService
import play.api.cache.Cache
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.{Valid, Invalid, Constraint}

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

	case class FormData(providerId: String, auhtUserId: String, inviteCode: String)

	def redeemInvite = Action { implicit request =>

		val authUserConstraint: Constraint[String] = Constraint("")({ authUserId =>
			val key = "pendingInvite_" + authUserId
			Cache.get(key).map(_ => Valid).getOrElse(Invalid("Your session expired, please sign in again!"))
		})

		val inviteCodeConstraint: Constraint[String] = Constraint("")({ inviteCode =>
			// TODO validate invite code
			//Invalid("Invalid invite code given!")
			Valid
		})

		val form = Form(
			mapping(
				"providerId" -> nonEmptyText,
				"authUserId" -> nonEmptyText.verifying(authUserConstraint),
				"inviteCode" -> nonEmptyText.verifying(inviteCodeConstraint)
			)(FormData.apply)(FormData.unapply)
		)

		form.bindFromRequest.fold(
			formWithErrors => { Redirect(routes.Application.signin).flashing("error" -> "foobar") },
			formData => {
				//PlayAuthenticate.getUserService.asInstanceOf[UserService].save(null, formData.inviteCode)
				Redirect(com.feth.play.module.pa.controllers.routes.Authenticate.authenticate(formData.providerId))
			}
		)

	}

}