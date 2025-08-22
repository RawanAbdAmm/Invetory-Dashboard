package com.example.inventorydashboard.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ItemsResponseDto(
    @SerializedName("Items_Master") val items: List<ItemDto> = emptyList()
)

data class ItemDto(
    @SerializedName("COMAPNYNO") val companyNo: String?,
    @SerializedName("ITEMNO")     val itemNo: String?,
    @SerializedName("NAME")       val name: String?,
    @SerializedName("CATEOGRYID") val categoryId: String?,
    @SerializedName("BARCODE")    val barcode: String?,
)
