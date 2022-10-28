package xyz.miyayu.android.registersimulator.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.miyayu.android.registersimulator.db.dao.CategoryDao
import xyz.miyayu.android.registersimulator.db.dao.ProductItemDao
import xyz.miyayu.android.registersimulator.db.dao.TaxRateDao
import xyz.miyayu.android.registersimulator.db.room.SettingRoomDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideSettingDatabase(
        @ApplicationContext context: Context
    ): SettingRoomDatabase {
        return Room
            .databaseBuilder(
                context,
                SettingRoomDatabase::class.java,
                "room_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: SettingRoomDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideTaxRateDao(database: SettingRoomDatabase): TaxRateDao {
        return database.taxRateDao()
    }

    @Singleton
    @Provides
    fun provideItemDao(database: SettingRoomDatabase): ProductItemDao {
        return database.productItem()
    }
}
