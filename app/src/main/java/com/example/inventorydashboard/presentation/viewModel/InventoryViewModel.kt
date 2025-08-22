package com.example.inventorydashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.CombinedItem
import com.example.inventorydashboard.domain.model.InventoryItem
import com.example.inventorydashboard.domain.usecase.GetBalancesUseCase
import com.example.inventorydashboard.domain.usecase.GetInventoryItemsUseCase
import com.example.inventorydashboard.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getItemsUseCase: GetInventoryItemsUseCase,
    private val getBalancesUseCase: GetBalancesUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<Result<List<CombinedItem>>>(Result.Loading)
    val items: StateFlow<Result<List<CombinedItem>>> = _items.asStateFlow()


    init {
        refresh()
    }

    fun refresh(cono: Int = 290, strno: Int = 1) {
        viewModelScope.launch {
            _items.value = Result.Loading
            try {
                val itemsResult = async { getItemsUseCase(cono, strno) }
                val balancesResult = async { getBalancesUseCase(cono, strno) }

                val items = itemsResult.await()
                val balances = balancesResult.await()

                val combined = combine(items, balances)
                _items.value = Result.Success(combined)

            } catch (e: Exception) {
                _items.value = Result.Error(e.message ?: "Unexpected error")
            }
        }
    }


    private fun combine(
        items: List<InventoryItem>,
        balances: List<BalanceItem>
    ): List<CombinedItem> {
        val qtyByItem: Map<String, Double> = balances
            .groupBy { it.itemCode }
            .mapValues { (_, list) -> list.sumOf { it.quantity } }

        return items.map { item ->
            CombinedItem(
                itemNo = item.itemNo,
                name = item.name,
                category = item.category,
                quantity = qtyByItem[item.itemNo] ?: 0.0
            )
        }.sortedBy { it.name }
    }
}
