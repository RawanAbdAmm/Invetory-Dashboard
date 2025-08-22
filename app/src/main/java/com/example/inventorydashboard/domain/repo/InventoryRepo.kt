package com.example.inventorydashboard.domain.repo

import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.InventoryItem

interface InventoryRepository {
    suspend fun getItems(cono: Int = 290, strno: Int = 1): List<InventoryItem>
    suspend fun getBalances(cono: Int = 290, strno: Int = 1): List<BalanceItem>
}
