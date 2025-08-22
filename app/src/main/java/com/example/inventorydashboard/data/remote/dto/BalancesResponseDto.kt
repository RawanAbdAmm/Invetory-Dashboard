package com.example.inventorydashboard.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BalancesResponseDto(
    @SerializedName("SalesMan_Items_Balance") val balances: List<BalanceDto> = emptyList()
)

data class BalanceDto(
    @SerializedName("COMAPNYNO") val companyNum: String?,
    @SerializedName("STOCK_CODE") val stockCode: String?,
    @SerializedName("ItemOCode") val itemOCode: String?,
    @SerializedName("QTY") val qty: String?
)
