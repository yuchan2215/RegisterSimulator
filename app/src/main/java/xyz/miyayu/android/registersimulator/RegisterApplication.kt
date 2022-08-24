package xyz.miyayu.android.registersimulator

import android.app.Application

class RegisterApplication : Application() {
    companion object {
        lateinit var instance: RegisterApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}