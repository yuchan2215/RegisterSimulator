package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.Embedded
import androidx.room.Relation
import java.math.BigDecimal
import java.math.RoundingMode

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
    private fun getUseTaxRate(): TaxRate? = taxRate ?: defaultCategoryDetail.taxRate

    fun getTax(): BigDecimal {
        val useTaxRate = getUseTaxRate()?.getBigDecimalRate()?.divide(
            100.toBigDecimal(),
            2,
            RoundingMode.HALF_EVEN
        ) ?: "0".toBigDecimal()
        return item.getBigDecimalPrice() * useTaxRate
    }

    fun getTaxIncludedPrice(): BigDecimal {
        return item.getBigDecimalPrice() + getTax()
    }
}
