package xyz.miyayu.android.registersimulator.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.miyayu.android.registersimulator.model.dao.CategoryDao
import xyz.miyayu.android.registersimulator.model.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.model.dao.TaxRateDao
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.model.entity.ProductItem
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.model.room.converter.DateConverter

@Database(
    version = 1,
    exportSchema = true,
    entities = [TaxRate::class, Category::class, ProductItem::class]
)
@TypeConverters(DateConverter::class)
abstract class SettingRoomDatabase : RoomDatabase() {
    abstract fun taxRateDao(): TaxRateDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productItem(): ProductItemDao

    companion object {
        @Volatile
        private var INSTANCE: SettingRoomDatabase? = null
        fun getDatabase(context: Context): SettingRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SettingRoomDatabase::class.java,
                    "room_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
