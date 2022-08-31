package xyz.miyayu.android.registersimulator.model.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Update
    suspend fun update(category: Category)

    @Query(GET_CATEGORIES_QUERY)
    suspend fun getCategories(): List<Category>

    @Query(GET_CATEGORIES_QUERY)
    fun getCategoriesFlow(): Flow<List<Category>>

    @Transaction
    @Query(GET_CATEGORIES_QUERY)
    suspend fun getCategoriesAndTaxRates(): List<CategoryAndTaxRate>

    @Transaction
    @Query(GET_CATEGORIES_QUERY)
    fun getCategoriesAndTaxRatesFlow(): Flow<List<CategoryAndTaxRate>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategory(categoryId: Int): Category

    companion object {
        const val GET_CATEGORIES_QUERY = "SELECT * FROM categories ORDER BY id ASC"
    }
}