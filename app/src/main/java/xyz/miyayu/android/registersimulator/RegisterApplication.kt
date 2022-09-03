package xyz.miyayu.android.registersimulator

import android.app.Application
import xyz.miyayu.android.registersimulator.model.room.SettingRoomDatabase

class RegisterApplication : Application() {
    companion object {
        lateinit var instance: RegisterApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val database: SettingRoomDatabase by lazy {
        SettingRoomDatabase.getDatabase(this)
    }
}
