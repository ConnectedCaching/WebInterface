package utilities

import play.api.libs.Codecs
import java.security.MessageDigest

object Gravatar {

	private lazy val md = MessageDigest.getInstance("MD5")

	private def hashFor(email: String): String = {
		val cleanEmail = email.toLowerCase.trim
		val hash = md.digest(cleanEmail.getBytes("utf-8"))
		Codecs.toHexString(hash)
	}

	def urlFor(email: String): String = urlFor(email, 30)

	def urlFor(email: String, size: Int): String = {
		val hash = hashFor(email)
		s"https://secure.gravatar.com/avatar/$hash?s=$size&d=identicon"
	}

}