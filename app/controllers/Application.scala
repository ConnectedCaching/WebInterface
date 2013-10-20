package controllers

import play.api._
import play.api.mvc._

object Application extends Controller with securesocial.core.SecureSocial  {

	def index = UserAwareAction { implicit request =>
		Ok(views.html.index(request.user))
	}

	def webInterface = SecuredAction { implicit request =>
		Ok(views.html.webInterface(request.user))
	}

}