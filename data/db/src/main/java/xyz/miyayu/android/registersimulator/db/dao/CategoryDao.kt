package xyz.miyayu.android.registersimulator.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import xyz.miyayu.android.registersimulator.model.Category
import xyz.miyayu.android.registersimulator.model.CategoryAndTaxRate

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

    @Query(GET_CATEGORY_QUERY)
    suspend fun getCategory(categoryId: Int): Category

    @Transaction
    @Query(GET_CATEGORY_QUERY)
    suspend fun getCategoryAndTaxRate(categoryId: Int): CategoryAndTaxRate

    companion object {
        const val GET_CATEGORIES_QUERY = "SELECT * FROM categories ORDER BY id ASC"
        const val GET_CATEGORY_QUERY = "SELECT * FROM categories WHERE id = :categoryId"
    }
}
