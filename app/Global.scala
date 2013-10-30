

import com.feth.play.module.pa.PlayAuthenticate
import play.api.Logger
import play.api.Application
import play.api.GlobalSettings

import services.AuthResolver

object Global extends GlobalSettings {

	override def onStart(app: Application) {
		Logger.info("Application has started")
		PlayAuthenticate.setResolver(new AuthResolver)
	}

	override def onStop(app: Application) {
		Logger.info("Application shutdown...")
	}

}
