package com.dripio

import android.app.Application
import com.dripio.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationInitialization : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationInitialization)
            modules(appModules)
        }
    }
}
