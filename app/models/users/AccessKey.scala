package models.users

import play.api.libs.json.Json
import play.ff.extensions.StringExtensions._
import org.joda.time.{DateTimeZone, DateTime}

case class AccessKey(
	name: String,
	keyId: String,
	key: String,
	validFrom: DateTime,
	validTo: DateTime
) {

	def isValid: Boolean = {
		val now = DateTime.now(DateTimeZone.UTC)
		now.isAfter(validFrom) && now.isBefore(validTo)
	}

}

object AccessKey {

	implicit val accessKeyFormat = Json.format[AccessKey]

	/**
	 * Creates an AccessKey with the given name and the default validity span of
	 * one year starting from the moment of creation
	 */
	def create(name: String): AccessKey = {
		val now = DateTime.now(DateTimeZone.UTC)
		AccessKey(
			name,
			CharSet.AlphaNumeric.atRandom(8),
			CharSet.AlphaNumeric.atRandom(64),
			now,
			now.plusYears(1)
		)
	}

	/**
	 * @return the default set of access keys for a new user account
	 */
	def createInitialSet: List[AccessKey] = {
		List(
			AccessKey.create("Desktop Client"),
			AccessKey.create("Browser Integration")
		)
	}

}