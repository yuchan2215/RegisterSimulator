package xyz.miyayu.android.registersimulator.repositories

import xyz.miyayu.android.registersimulator.model.dao.ProductItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val productItemDao: ProductItemDao
) {
    fun getAllItemDetailsFlow() = productItemDao.getAllItemDetailsFlow()
}
