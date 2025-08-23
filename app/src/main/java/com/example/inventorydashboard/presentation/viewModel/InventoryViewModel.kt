package com.example.inventorydashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorydashboard.domain.model.BalanceItem
import com.example.inventorydashboard.domain.model.CombinedItem
import com.example.inventorydashboard.domain.model.InventoryItem
import com.example.inventorydashboard.domain.usecase.GetBalancesUseCase
import com.example.inventorydashboard.domain.usecase.GetCombinedUseCase
import com.example.inventorydashboard.domain.usecase.GetInventoryItemsUseCase
import com.example.inventorydashboard.domain.usecase.GetLastSyncFormattedUseCase
import com.example.inventorydashboard.domain.usecase.InsertCombineUseCase
import com.example.inventorydashboard.domain.usecase.SaveLastSyncUseCase
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
    private val getBalancesUseCase: GetBalancesUseCase,
    private val insertCombineUseCase: InsertCombineUseCase,
    private val getCombinedUseCase: GetCombinedUseCase,
    private val getLastSyncFormattedUseCase: GetLastSyncFormattedUseCase,
    private val saveLastSyncUseCase: SaveLastSyncUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<Result<List<CombinedItem>>>(Result.Loading)
    val items: StateFlow<Result<List<CombinedItem>>> = _items.asStateFlow()

    private var allItems: List<CombinedItem> = emptyList()

    private val _categoryOptions = MutableStateFlow<List<String>>(emptyList())
    val categoryOptions: StateFlow<List<String>> = _categoryOptions.asStateFlow()

    private var currentSearch: String = ""
    private var currentCategory: String = ""

    private val _lastSyncText = MutableStateFlow("-")
    val lastSyncText: StateFlow<String> = _lastSyncText


    init {
        refresh()
    }

    fun refresh(cono: Int = 290, strno: Int = 1, isPulled: Boolean = false) {
        viewModelScope.launch {
            _items.value = Result.Loading
            try {
                val cachedItem = getCombinedUseCase()

                if (cachedItem.isNotEmpty() && !isPulled) {
                    allItems = cachedItem
                } else {
                    val itemsDef = async { getItemsUseCase(cono, strno) }
                    val balancesDef = async { getBalancesUseCase(cono, strno) }

                    val items = itemsDef.await()
                    val balances = balancesDef.await()

                    allItems = combine(items, balances).sortedBy { it.name }
                    insertCombineUseCase(combine(items, balances).sortedBy { it.name })
                }
                val category = allItems.mapNotNull { it.itemK?.trim() }
                    .filter { it.isNotEmpty() }
                    .distinct()
                    .sorted()

                _categoryOptions.value = listOf("الكل") + category
                currentSearch = ""
                currentCategory = ""

                saveLastSyncUseCase()
                val text = getLastSyncFormattedUseCase()
                _lastSyncText.value = text

                applyFilters()
            } catch (e: Exception) {
                _items.value = Result.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun setCategory(categoryOrAll: String) {
        currentCategory = if (categoryOrAll == "الكل") "" else categoryOrAll
        applyFilters()
    }

    private fun applyFilters() {
        _items.value = Result.Success(
            allItems.filter { item ->
                (currentSearch.isEmpty() || item.name.trim().lowercase()
                    .startsWith(currentSearch)) &&
                        (currentCategory.isEmpty() || item.itemK?.trim() == currentCategory)
            }
        )
    }


    fun search(text: String) {
        val value = text.trim().lowercase()
        if (value.isEmpty()) {
            _items.value = Result.Success(allItems)
        } else {
            val filtered = allItems.filter { it.matches(value) }
            _items.value = Result.Success(filtered)
        }
    }

    private fun CombinedItem.matches(text: String): Boolean =
        name.contains(text, ignoreCase = true)


    private fun combine(
        items: List<InventoryItem>,
        balances: List<BalanceItem>
    ): List<CombinedItem> {
        val qtyByItem = balances
            .groupBy { it.itemCode }
            .mapValues { (_, list) -> list.sumOf { it.quantity } }

        return items.map { item ->
            CombinedItem(
                itemNo = item.itemNo,
                name = item.name,
                category = item.category,
                quantity = qtyByItem[item.itemNo] ?: 0.0,
                itemK = item.itemK,
            )
        }
    }
}