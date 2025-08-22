package com.example.inventorydashboard.data.repo

import com.example.inventorydashboard.data.mapper.toBalanceItem
import com.example.inventorydashboard.data.mapper.toInventoryItem
import com.example.inventorydashboard.data.remote.ApiService
import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.InventoryItem
import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject

class InventoryRepoImp @Inject constructor(
    private val api: ApiService
) : InventoryRepository {

    override suspend fun getItems(cono: Int, strno: Int): List<InventoryItem> =
        api.getItems(cono, strno).items.mapNotNull { it.toInventoryItem() }

    override suspend fun getBalances(cono: Int, strno: Int): List<BalanceItem> =
        api.getBalances(cono, strno).balances.mapNotNull { it.toBalanceItem() }

}
