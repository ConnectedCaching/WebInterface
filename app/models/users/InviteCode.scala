package models.users

import play.api.libs.json.Json
import play.api.data.validation.{Invalid, Valid, Constraint}
import play.api.cache.Cache
import reactivemongo.core.commands.Count
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.data.Form
import play.api.data.Forms._
import reactivemongo.bson.{BSONDocument, BSONString}
import scala.Some
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current

case class InviteCode(providerId: String, authUserId: String, code: String)

object InviteCode {

	implicit val inviteCodeFormat = Json.format[InviteCode]

	def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("invite_codes")

	val authUserConstraint: Constraint[String] = Constraint("")({ authUserId =>
		val key = "pendingInvite_" + authUserId
		Cache.get(key).map(_ => Valid).getOrElse(Invalid("Your session expired, please sign in again!"))
	})

	val inviteCodeConstraint: Constraint[String] = Constraint("")({ inviteCode =>
		val futureCount = ReactiveMongoPlugin.db.command(Count("invite_codes", Some(BSONDocument("code" -> BSONString(inviteCode)))))
		val result = futureCount.map { count =>
			if (count < 1) Invalid("Invalid invite code given!") else Valid
		}
		Await.result(result, Duration.Inf)
	})

	val redeemForm = Form(
		mapping(
			"providerId" -> nonEmptyText,
			"authUserId" -> nonEmptyText.verifying(authUserConstraint),
			"inviteCode" -> nonEmptyText.verifying(inviteCodeConstraint)
		)(InviteCode.apply)(InviteCode.unapply)
	)

}