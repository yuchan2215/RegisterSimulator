package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.Embedded
import androidx.room.Relation
import xyz.miyayu.android.registersimulator.util.DecimalUtils.convertToDecimalPoint
import xyz.miyayu.android.registersimulator.util.DecimalUtils.convertZeroIfNull
import java.math.BigDecimal

data class ProductItemDetail(
    @Embedded val item: ProductItem,
    @Relation(
        entity = Category::class,
        parentColumn = "category_id",
        entityColumn = "id"
    ) val defaultCategoryDetail: CategoryAndTaxRate,
    @Relation(
        entity = TaxRate::class,
        parentColumn = "tax_id",
        entityColumn = "id"
    ) val taxRate: TaxRate? = null
) {
    /**使用する税率を取得する。*/
    private fun getUseTaxRate(): TaxRate? = taxRate ?: defaultCategoryDetail.taxRate

    /**消費税を計算*/
    @Suppress("MemberVisibilityCanBePrivate")
    fun getTax(): BigDecimal {
        val useTaxRate =
            getUseTaxRate()?.getBigDecimalPercentRate().convertZeroIfNull().convertToDecimalPoint()
        return item.getBigDecimalPrice() * useTaxRate
    }

    /**税込価格を計算*/
    fun getTaxIncludedPrice(): BigDecimal {
        return item.getBigDecimalPrice() + getTax()
    }
}
