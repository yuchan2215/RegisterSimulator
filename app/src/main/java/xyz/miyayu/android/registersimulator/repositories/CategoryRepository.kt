package xyz.miyayu.android.registersimulator.repositories

import xyz.miyayu.android.registersimulator.model.dao.CategoryDao
import xyz.miyayu.android.registersimulator.model.entity.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    suspend fun update(category: Category) {
        categoryDao.update(category)
    }

    suspend fun getCategories() = categoryDao.getCategories()

    fun getCategoriesFlow() = categoryDao.getCategoriesFlow()

    suspend fun getCategoriesAndTaxRates() = categoryDao.getCategoriesAndTaxRates()

    fun getCategoriesAndTaxRatesFlow() = categoryDao.getCategoriesAndTaxRatesFlow()

    suspend fun getCategory(categoryId: Int) = categoryDao.getCategory(categoryId)

    suspend fun getCategoryAndTaxRate(categoryId: Int) =
        categoryDao.getCategoryAndTaxRate(categoryId)
}
