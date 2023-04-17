package com.kgh.prototype

import android.app.Application

class HexaApplication : Application() {
    companion object {
        lateinit var instance: HexaApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}