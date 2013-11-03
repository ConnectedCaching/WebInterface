package models.users

import play.api.libs.json.Json
import play.ff.extensions.StringExtensions._

case class AccessKey(
	name: String,
	keyId: String,
	key: String
)

object AccessKey {

	def createInitialSet: List[AccessKey] = {
		List(
			AccessKey("Desktop Client", CharSet.AlphaNumeric.atRandom(8), CharSet.AlphaNumeric.atRandom(64)),
			AccessKey("Browser Integration", CharSet.AlphaNumeric.atRandom(8), CharSet.AlphaNumeric.atRandom(64))
		)
	}

	implicit val accessKeyFormat = Json.format[AccessKey]

}