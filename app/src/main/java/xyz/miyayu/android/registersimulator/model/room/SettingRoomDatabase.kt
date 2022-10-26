package xyz.miyayu.android.registersimulator.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.miyayu.android.registersimulator.Category
import xyz.miyayu.android.registersimulator.ProductItem
import xyz.miyayu.android.registersimulator.TaxRate
import xyz.miyayu.android.registersimulator.model.dao.CategoryDao
import xyz.miyayu.android.registersimulator.model.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.model.dao.TaxRateDao
import xyz.miyayu.android.registersimulator.model.room.converter.DateConverter
import xyz.miyayu.android.registersimulator.model.room.converter.PriceConverter

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
