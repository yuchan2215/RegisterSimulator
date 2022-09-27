package xyz.miyayu.android.registersimulator.repositories

import kotlinx.coroutines.flow.Flow
import xyz.miyayu.android.registersimulator.model.dao.CategoryDao
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate
import javax.inject.Inject

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun delete(category: Category)
    suspend fun update(category: Category)
    suspend fun getCategories(): List<Category>
    fun getCategoriesFlow(): Flow<List<Category>>
    suspend fun getCategoriesAndTaxRates(): List<CategoryAndTaxRate>
    fun getCategoriesAndTaxRatesFlow(): Flow<List<CategoryAndTaxRate>>
    suspend fun getCategory(categoryId: Int): Category
}

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    override suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    override suspend fun update(category: Category) {
        categoryDao.update(category)
    }

    override suspend fun getCategories() = categoryDao.getCategories()

    override fun getCategoriesFlow() = categoryDao.getCategoriesFlow()

    override suspend fun getCategoriesAndTaxRates() = categoryDao.getCategoriesAndTaxRates()

    override fun getCategoriesAndTaxRatesFlow() = categoryDao.getCategoriesAndTaxRatesFlow()

    override suspend fun getCategory(categoryId: Int) = categoryDao.getCategory(categoryId)
}
