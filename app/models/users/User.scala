package models.users

import com.feth.play.module.pa.user.AuthUserIdentity
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.core.commands.LastError
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current
import scala.concurrent.duration.Duration
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

case class User(
	_id: Option[BSONObjectID],
	logins: List[UserLogin],
	accessKeys: List[AccessKey],
	collections: List[models.collections.Collection]
)

object User {

	implicit val userFormat = Json.format[User]

	def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("users")

	def findBlocking(identity: AuthUserIdentity): Option[User] = {
		val query = Json.obj("logins.provider" -> identity.getProvider, "logins.userId" -> identity.getId)
		val user = collection.find(query).one[User]
		Await.result(user, Duration.Inf)
	}

	def create(login: UserLogin): Future[LastError] = {
		val user = User(
			None,
			List(login),
			AccessKey.createInitialSet,
			models.collections.Collection.createInitialSet
		)
		collection.insert(user)
	}

}