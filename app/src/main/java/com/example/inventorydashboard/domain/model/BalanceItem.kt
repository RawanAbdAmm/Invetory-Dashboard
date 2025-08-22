package com.example.inventorydashboard.domain.model

data class BalanceItem(
    val companyNo: String,
    val itemCode: String,
    val stockCode: String,
    val quantity: Double
)