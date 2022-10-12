package xyz.miyayu.android.registersimulator.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "items")
data class ProductItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "jan") val janCode: Long? = null, // 野菜などJANがつかない場合もある
    @ColumnInfo(name = "name") val itemName: String,
    val categoryId: Int? = null,
    val price: Long, // 念の為高額商品にも対応できるようにする。
    val taxId: Int? = null, // カテゴリのデフォルト税率を利用する際はnullになる。
    @ColumnInfo(name = "make_date_long") val makeDate: Date,
    @ColumnInfo(name = "update_date_long") val updateDate: Date
)
