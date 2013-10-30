package controllers

import play.api._
import play.api.mvc._

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
		Redirect(routes.Application.index).flashing(
			"error" -> "You need to accept the OAuth connection in order to use this website!"
		)
	}

}