package xyz.miyayu.android.registersimulator.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.miyayu.android.registersimulator.db.dao.CategoryDao
import xyz.miyayu.android.registersimulator.db.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.db.dao.TaxRateDao
import xyz.miyayu.android.registersimulator.db.room.converter.DateConverter
import xyz.miyayu.android.registersimulator.db.room.converter.PriceConverter
import xyz.miyayu.android.registersimulator.model.Category
import xyz.miyayu.android.registersimulator.model.ProductItem
import xyz.miyayu.android.registersimulator.model.TaxRate

@Database(
    version = 1,
    exportSchema = true,
    entities = [TaxRate::class, Category::class, ProductItem::class]
)
@TypeConverters(DateConverter::class, PriceConverter::class)
abstract class SettingRoomDatabase : RoomDatabase() {
    abstract fun taxRateDao(): TaxRateDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productItem(): ProductItemDao
}
