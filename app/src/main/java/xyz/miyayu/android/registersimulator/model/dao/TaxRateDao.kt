package xyz.miyayu.android.registersimulator.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.miyayu.android.registersimulator.model.entity.TaxRate

@Dao
interface TaxRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taxRate: TaxRate)

    @Query("SELECT * FROM taxes WHERE id = :id")
    suspend fun getTaxRate(id: Int): TaxRate?

    @Query("SELECT * FROM taxes ORDER BY id ASC")
    fun getItemsFlow(): Flow<List<TaxRate>>

    @Query("SELECT * FROM taxes ORDER BY id ASC")
    suspend fun getItems(): List<TaxRate>
}
