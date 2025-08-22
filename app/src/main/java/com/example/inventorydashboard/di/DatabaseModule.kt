package com.example.inventorydashboard.di

import android.content.Context
import androidx.room.Room
import com.example.inventorydashboard.data.local.AppDatabase
import com.example.inventorydashboard.data.local.dao.CombinedItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "inventory_db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides fun provideCombinedDao(db: AppDatabase): CombinedItemDao = db.combinedDao()
}