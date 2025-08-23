package com.example.inventorydashboard.data.mapper

import com.example.inventorydashboard.data.local.dto.CombinedItemDto
import com.example.inventorydashboard.data.local.dto.SyncInfoEntity
import com.example.inventorydashboard.data.remote.dto.BalanceDto
import com.example.inventorydashboard.data.remote.dto.ItemDto
import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.CombinedItem
import com.example.inventorydashboard.domain.model.InventoryItem

fun ItemDto.toInventoryItem(): InventoryItem? {
    val itemNo = itemNo?.trim().orEmpty()
    val itemName = name?.trim().orEmpty()
    if (itemNo.isEmpty() || itemName.isEmpty()) return null
    return InventoryItem(
        companyNo = companyNo ?: "",
        itemNo = itemNo,
        name = itemName,
        category = categoryId?.ifBlank { null },
        barcode = barcode?.ifBlank { null },
        itemK = itemK?.ifBlank { null }
    )
}

fun BalanceDto.toBalanceItem(): BalanceItem? {
    val id = itemOCode?.trim().orEmpty()
    if (id.isEmpty()) return null
    val stockCode = (stockCode ?: "TOTAL").ifBlank { "TOTAL" }
    val quantity = qty?.trim()?.replace(',', '.')?.toDoubleOrNull() ?: 0.0
    return BalanceItem(
        companyNo = companyNum ?: "",
        itemCode = id,
        stockCode = stockCode,
        quantity = quantity
    )
}

fun CombinedItem.toCombineItemDto(): CombinedItemDto? {
    return CombinedItemDto(
        itemNo = itemNo,
        name = name,
        category = category,
        quantity = quantity,
        itemK = itemK
    )
}

fun CombinedItemDto.toCombineItem(): CombinedItem? {
    return CombinedItem(
        itemNo = itemNo,
        name = name,
        category = category,
        quantity = quantity,
        itemK = itemK
    )

}

fun Long.toSyncInfoEntity(): SyncInfoEntity = SyncInfoEntity(lastSync = this)
fun SyncInfoEntity.toLastSyncMillis(): Long = lastSync