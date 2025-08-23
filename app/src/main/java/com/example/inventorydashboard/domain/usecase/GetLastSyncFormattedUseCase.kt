package com.example.inventorydashboard.domain.usecase

import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject

class GetLastSyncFormattedUseCase @Inject constructor(
    private val repo: InventoryRepository
) {
    suspend operator fun invoke(): String {
        return repo.getLastSyncFormatted()
    }
}
