package xyz.miyayu.android.registersimulator.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryDetail(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "default_tax_rate_id",
        entityColumn = "id"
    )
    val taxRate: TaxRate?
)
