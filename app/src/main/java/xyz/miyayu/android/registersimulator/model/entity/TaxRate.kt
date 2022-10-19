package xyz.miyayu.android.registersimulator.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/**
 * 税金のレートを保持するためのクラス
 */
@Parcelize
@Entity(tableName = "taxes")
data class TaxRate(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val rate: String
) : Parcelable {
    fun getBigDecimalRate(): BigDecimal {
        return BigDecimal(rate)
    }
}
