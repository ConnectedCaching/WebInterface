package models.users

import org.joda.time.DateTime
import play.api.libs.json.Json

case class UserLogin(
	provider: String,
	userId: String,
	lastVerified: DateTime
)

object UserLogin {

	implicit val userLoginFormat = Json.format[UserLogin]

}