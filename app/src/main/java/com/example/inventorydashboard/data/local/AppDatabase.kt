package com.example.inventorydashboard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventorydashboard.data.local.dao.CombinedItemDao
import com.example.inventorydashboard.data.local.dto.CombinedItemDto


@Database(
    entities = [CombinedItemDto::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun combinedDao(): CombinedItemDao
}
