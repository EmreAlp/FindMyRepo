package com.ing.findmyrepo

import android.app.Application
import com.ing.findmyrepo.module.AppModule
import com.ing.findmyrepo.module.DataBaseModule
import com.ing.findmyrepo.module.NetworkModule
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FindMyRepoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)

        val appModule = AppModule().appModule
        val networkModule = NetworkModule().networkModule
        val dataBaseModule = DataBaseModule().dataBaseModule

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    dataBaseModule
                )
            )
        }
    }
}