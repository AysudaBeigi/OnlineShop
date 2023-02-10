package com.example.onlineshop


import android.app.Application
import com.example.onlineshop.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class OnlineShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@OnlineShopApplication)
            modules(
                listOf(
                    retrofitModule,
                )
            )
        }

    }

}