package com.example.inventorydashboard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inventorydashboard.data.local.dto.CombinedItemDto


@Dao
interface CombinedItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<CombinedItemDto>)

    @Query("SELECT * FROM combined_items")
    suspend fun getAllItems(): List<CombinedItemDto>
}