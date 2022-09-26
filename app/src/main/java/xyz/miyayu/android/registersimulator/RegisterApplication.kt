package xyz.miyayu.android.registersimulator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import xyz.miyayu.android.registersimulator.model.room.SettingRoomDatabase

@HiltAndroidApp
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
