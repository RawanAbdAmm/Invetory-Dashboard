package com.example.inventorydashboard.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "combined_items")
data class CombinedItemDto(
    @PrimaryKey val itemNo: String,
    val name: String,
    val category: String?,
    val quantity: Double,
    val itemK: String?
)