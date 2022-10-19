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
        janCode: Long?,
        itemName: String,
        price: BigDecimal,
        categoryId: Int,
        taxRateId: Int?
    ) {
        val item = ProductItem(
            janCode = janCode,
            itemName = itemName,
            price = price.toLong(),
            categoryId = categoryId,
            taxId = taxRateId,
            makeDate = Date(),
            updateDate = Date(),
        )
        productItemDao.insert(item)
    }
}
