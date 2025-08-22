package com.example.inventorydashboard.domain.model

data class CombinedItem(
    val itemNo: String,
    val name: String,
    val category: String?,
    val quantity: Double
)
