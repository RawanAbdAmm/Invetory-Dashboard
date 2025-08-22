package com.example.inventorydashboard.domain.usecase

import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject


class GetCombinedUseCase @Inject constructor(
    private val repo: InventoryRepository
) {
    suspend operator fun invoke() =
        repo.getCombine()
}