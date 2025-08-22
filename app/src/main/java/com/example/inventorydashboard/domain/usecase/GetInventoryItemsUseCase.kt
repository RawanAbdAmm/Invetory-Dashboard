package com.example.inventorydashboard.domain.usecase

import com.example.inventorydashboard.domain.model.InventoryItem
import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject

class GetInventoryItemsUseCase @Inject constructor(
    private val repo: InventoryRepository
) {
    suspend operator fun invoke(cono: Int = 290, strno: Int = 1): List<InventoryItem> =
        repo.getItems(cono, strno)
}
