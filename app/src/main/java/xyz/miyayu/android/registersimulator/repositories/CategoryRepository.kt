package xyz.miyayu.android.registersimulator.repositories

import xyz.miyayu.android.registersimulator.RegisterApplication
import xyz.miyayu.android.registersimulator.model.entity.Category

object CategoryRepository {
    private fun getCategoryDao() = RegisterApplication.instance.database.categoryDao()

    suspend fun insert(category: Category){
        getCategoryDao().insert(category)
    }

    suspend fun delete(category: Category){
        getCategoryDao().delete(category)
    }

    suspend fun update(category: Category) {
        getCategoryDao().update(category)
    }

    suspend fun getCategories() = getCategoryDao().getCategories()

    fun getCategoriesFlow() = getCategoryDao().getCategoriesFlow()

    suspend fun getCategoriesAndTaxRates() = getCategoryDao().getCategoriesAndTaxRates()

    fun getCategoriesAndTaxRatesFlow() = getCategoryDao().getCategoriesAndTaxRatesFlow()

    suspend fun getCategory(categoryId: Int) = getCategoryDao().getCategory(categoryId)
}