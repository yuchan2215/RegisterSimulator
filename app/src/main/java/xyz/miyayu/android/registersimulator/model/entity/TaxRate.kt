package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

/**
 * 税金のレートを保持するためのクラス
 */
@Entity(tableName = "taxes")
data class TaxRate(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val rate: String
) {
    fun getBigDecimalRate(): BigDecimal {
        return BigDecimal(rate)
    }
}
