package com.example.inventorydashboard.data.repo

import com.example.inventorydashboard.data.local.dao.CombinedItemDao
import com.example.inventorydashboard.data.mapper.toBalanceItem
import com.example.inventorydashboard.data.mapper.toCombineItem
import com.example.inventorydashboard.data.mapper.toCombineItemDto
import com.example.inventorydashboard.data.mapper.toInventoryItem
import com.example.inventorydashboard.data.mapper.toLastSyncMillis
import com.example.inventorydashboard.data.mapper.toSyncInfoEntity
import com.example.inventorydashboard.data.remote.ApiService
import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.CombinedItem
import com.example.inventorydashboard.domain.model.InventoryItem
import com.example.inventorydashboard.domain.repo.InventoryRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class InventoryRepoImp @Inject constructor(
    private val api: ApiService,
    private val localData: CombinedItemDao
) : InventoryRepository {

    override suspend fun getItems(cono: Int, strno: Int): List<InventoryItem> =
        api.getItems(cono, strno).items.mapNotNull { it.toInventoryItem() }

    override suspend fun getBalances(cono: Int, strno: Int): List<BalanceItem> =
        api.getBalances(cono, strno).balances.mapNotNull { it.toBalanceItem() }

    override suspend fun insertCombine(list: List<CombinedItem>) {
        localData.insertItems(list.mapNotNull { it.toCombineItemDto() })
    }

    override suspend fun getCombine(): List<CombinedItem> {
        return localData.getAllItems().mapNotNull { it.toCombineItem() }
    }

    override suspend fun saveLastSync() {
        localData.saveLastSync(System.currentTimeMillis().toSyncInfoEntity())
    }

    override suspend fun getLastSyncFormatted(): String {
        val lastSync = localData.getLastSync()?.toLastSyncMillis() ?: 0L
        return if (lastSync == 0L) "-"
        else SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date(lastSync))
    }
}

