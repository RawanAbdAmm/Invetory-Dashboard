package com.example.inventorydashboard.domain.usecase

import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.repo.InventoryRepository
import javax.inject.Inject

class GetBalancesUseCase @Inject constructor(
    private val repo: InventoryRepository
) {
    suspend operator fun invoke(cono: Int = 290, strno: Int = 1): List<BalanceItem> =
        repo.getBalances(cono, strno)
}
