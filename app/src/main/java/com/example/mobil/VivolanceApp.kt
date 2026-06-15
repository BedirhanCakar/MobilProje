package com.example.mobil

import android.app.Application
import com.example.mobil.di.databaseModule
import com.example.mobil.di.repositoryModule
import com.example.mobil.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class VivolanceApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@VivolanceApp)
            modules(listOf(databaseModule, repositoryModule, viewModelModule))
        }
    }
}
