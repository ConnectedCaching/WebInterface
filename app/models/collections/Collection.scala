package models.collections

import play.api.libs.json.Json

case class Collection(
	name: String,
	removable: Boolean,
	allowsSubCollections: Boolean,
	collections: List[Collection]
)

object Collection {

	implicit val collectionFormat = Json.format[Collection]

	def createInitialSet: List[Collection] = {
		List(
			Collection("Browser Imports", false, false, List()),
			Collection("My Collections", false, true, List())
		)
	}

}