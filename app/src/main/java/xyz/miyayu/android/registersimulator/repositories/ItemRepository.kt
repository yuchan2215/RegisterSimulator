package xyz.miyayu.android.registersimulator.repositories

import xyz.miyayu.android.registersimulator.model.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.model.entity.ProductItem
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val productItemDao: ProductItemDao
) {
    fun getAllItemDetailsFlow() = productItemDao.getAllItemDetailsFlow()

    suspend fun getItem(id: Int) = productItemDao.getItem(id)

    suspend fun addItem(
        itemId: Int? = null,
        janCode: Long?,
        itemName: String,
        price: BigDecimal,
        categoryId: Int,
        taxRateId: Int?,
        makeDate: Date
    ) {
        val item = ProductItem(
            id = itemId,
            janCode = janCode,
            itemName = itemName,
            price = price.toLong(),
            categoryId = categoryId,
            taxId = taxRateId,
            makeDate = makeDate,
            updateDate = Date(),
        )
        productItemDao.insert(item)
    }
}
