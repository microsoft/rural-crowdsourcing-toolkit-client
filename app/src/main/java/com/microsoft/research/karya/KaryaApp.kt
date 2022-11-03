package com.microsoft.research.karya

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.microsoft.research.karya.data.manager.SyncDelegatingWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KaryaApp : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: SyncDelegatingWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG).setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        /**
         * For Debug builds, print the logs to the Logcat and
         * for release builds only Log.ERROR will be sent to [FirebaseCrashlytics]
         */
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree())
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            /** Send exception report only for Timber.e, Timber.v, d, i, w, a will be ignored by [FirebaseCrashlytics] */
            if (priority == Log.ERROR && t != null) {
                FirebaseCrashlytics.getInstance().recordException(t)
            }
        }
    }
}
