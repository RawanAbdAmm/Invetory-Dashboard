package com.example.inventorydashboard.domain.model

data class InventoryItem(
    val companyNo: String,
    val itemNo: String,
    val name: String,
    val category: String?,
    val barcode: String?
)
