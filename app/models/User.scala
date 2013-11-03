package models

import com.feth.play.module.pa.user.AuthUserIdentity
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.core.commands.LastError
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

case class User(
	logins: List[UserLogin],
	accessKeys: List[AccessKey]
)

object User {

	def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("users")

	def findByAuthUserIdentity(identity: AuthUserIdentity): Option[User] = {
		//Some(User(identity.getProvider + "/" + identity.getId))
		None
	}

	def create(login: UserLogin): Future[LastError] = {
		val user = User(
			List(login),
			AccessKey.createInitialSet
		)
		collection.insert(user)
	}

	implicit val userFormat = Json.format[User]

}