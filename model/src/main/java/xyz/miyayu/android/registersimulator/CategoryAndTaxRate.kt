package xyz.miyayu.android.registersimulator

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryAndTaxRate(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "default_tax_rate_id",
        entityColumn = "id"
    )
    val taxRate: TaxRate?
)
