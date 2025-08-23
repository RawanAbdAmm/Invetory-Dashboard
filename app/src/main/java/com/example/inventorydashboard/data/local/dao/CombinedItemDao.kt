package com.example.inventorydashboard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inventorydashboard.data.local.dto.CombinedItemDto
import com.example.inventorydashboard.data.local.dto.SyncInfoEntity


@Dao
interface CombinedItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<CombinedItemDto>)

    @Query("SELECT * FROM combined_items")
    suspend fun getAllItems(): List<CombinedItemDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastSync(entity: SyncInfoEntity)

    @Query("SELECT * FROM sync_info WHERE id = 0 LIMIT 1")
    suspend fun getLastSync(): SyncInfoEntity?
}