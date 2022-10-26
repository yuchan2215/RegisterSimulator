package xyz.miyayu.android.registersimulator.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import xyz.miyayu.android.registersimulator.ProductItem
import xyz.miyayu.android.registersimulator.ProductItemDetail

@Dao
interface ProductItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ProductItem)

    @Delete
    suspend fun delete(item: ProductItem)

    @Update
    suspend fun update(item: ProductItem)

    @Query(GET_ALL_ITEMS_QUERY)
    suspend fun getAllItems(): List<ProductItem>

    @Query(GET_ALL_ITEMS_QUERY)
    fun getAllItemsFlow(): Flow<List<ProductItem>>

    @Query(GET_ITEMS_QUERY)
    suspend fun getItems(janCode: Long): List<ProductItem>

    @Query(GET_ITEMS_QUERY)
    fun getItemsFlow(janCode: Long): Flow<List<ProductItem>>

    @Query(GET_ITEM_QUERY)
    suspend fun getItem(id: Int): ProductItem

    @Query(GET_ITEM_QUERY)
    fun getItemFlow(id: Int): Flow<ProductItem>

    @Transaction
    @Query(GET_ALL_ITEMS_QUERY)
    suspend fun getAllItemDetails(): List<ProductItemDetail>

    @Transaction
    @Query(GET_ALL_ITEMS_QUERY)
    fun getAllItemDetailsFlow(): Flow<List<ProductItemDetail>>

    @Transaction
    @Query(GET_ITEMS_QUERY)
    suspend fun getItemDetails(janCode: Long): List<ProductItemDetail>

    @Transaction
    @Query(GET_ITEMS_QUERY)
    fun getItemDetailsFlow(janCode: Long): Flow<List<ProductItemDetail>>

    @Transaction
    @Query(GET_ITEM_QUERY)
    suspend fun getItemDetail(id: Int): ProductItemDetail

    @Transaction
    @Query(GET_ITEM_QUERY)
    fun getItemDetailFlow(id: Int): Flow<ProductItemDetail>

    companion object {
        const val GET_ALL_ITEMS_QUERY = "SELECT * FROM items ORDER BY id ASC"
        const val GET_ITEMS_QUERY = "SELECT * FROM items WHERE jan = :janCode"
        const val GET_ITEM_QUERY = "SELECT * FROM items WHERE id = :id"
    }
}
