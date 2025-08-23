package com.example.inventorydashboard.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_info")
data class SyncInfoEntity(
    @PrimaryKey val id: Int = 0,
    val lastSync: Long
)
