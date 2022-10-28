package xyz.miyayu.android.registersimulator.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import xyz.miyayu.android.registersimulator.utils.ResourceService
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
    fun getBigDecimalPercentRate(): BigDecimal {
        return BigDecimal(rate)
    }

    companion object {
        /**
         * 税率(**%)の形式でプレビューを作成する。
         */
        fun TaxRate.getPreview(resourceService: ResourceService): String =
            resourceService.getResources()
                .getString(R.string.category_and_tax_rate_preview, title, rate)
    }
}
