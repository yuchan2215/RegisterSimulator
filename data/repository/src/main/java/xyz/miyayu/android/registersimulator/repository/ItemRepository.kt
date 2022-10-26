package xyz.miyayu.android.registersimulator.repository

import xyz.miyayu.android.registersimulator.db.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.model.ProductItem
import xyz.miyayu.android.registersimulator.model.price.WithoutTaxPrice
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
        price: WithoutTaxPrice,
        categoryId: Int,
        taxRateId: Int?,
        makeDate: Date
    ) {
        val item = ProductItem(
            id = itemId,
            janCode = janCode,
            itemName = itemName,
            price = price,
            categoryId = categoryId,
            taxId = taxRateId,
            makeDate = makeDate,
            updateDate = Date(),
        )
        productItemDao.insert(item)
    }
}
