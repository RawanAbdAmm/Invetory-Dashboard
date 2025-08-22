package com.example.inventorydashboard.domain.usecase

import com.example.inventorydashboard.domain.model.CombinedItem
import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject

class InsertCombineUseCase @Inject constructor(
    private val repo: InventoryRepository
) {
    suspend operator fun invoke(list: List<CombinedItem>) =
        repo.insertCombine(list)
}