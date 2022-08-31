package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val categoryId: Int,
    val name: String,
    @ColumnInfo(name = "default_tax_rate_id")
    val defaultTaxRateId: Int?,
)