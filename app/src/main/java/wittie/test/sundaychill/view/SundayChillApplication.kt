package wittie.test.sundaychill.view

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level.DEBUG
import wittie.test.sundaychill.domain.injection.appModules

class SundayChillApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(DEBUG)
            androidContext(this@SundayChillApplication)
            modules(appModules)
        }
    }

}