package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.Embedded
import androidx.room.Relation

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
)
