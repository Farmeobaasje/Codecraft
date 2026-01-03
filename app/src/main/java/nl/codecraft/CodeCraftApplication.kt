package nl.codecraft

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CodeCraftApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: Replace with BuildConfig.DEBUG when BuildConfig is available
        Timber.plant(Timber.DebugTree())
        Timber.d("CodeCraftApplication created")
    }
}
