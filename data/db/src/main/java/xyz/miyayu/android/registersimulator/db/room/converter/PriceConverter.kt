package xyz.miyayu.android.registersimulator.db.room.converter

import androidx.room.TypeConverter
import xyz.miyayu.android.registersimulator.model.price.WithoutTaxPrice

internal class PriceConverter {
    @TypeConverter
    fun fromString(value: String?): WithoutTaxPrice? {
        return value?.let { WithoutTaxPrice(value) }
    }

    @TypeConverter
    fun priceToString(value: WithoutTaxPrice?): String? {
        return value?.toString()
    }
}
