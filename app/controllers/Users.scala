package controllers

import play.api.mvc._

object Users extends Controller with Authentication {

	def accessKeys = Authenticated { implicit user => implicit request =>
		Ok(views.html.users.accessKeys(user))
	}

}
