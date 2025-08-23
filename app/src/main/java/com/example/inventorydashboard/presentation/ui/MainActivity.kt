package com.example.inventorydashboard.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorydashboard.databinding.ActivityMainBinding
import com.example.inventorydashboard.presentation.adapter.InventoryAdapter
import com.example.inventorydashboard.presentation.extentions.onItemSelected
import com.example.inventorydashboard.presentation.extentions.showErrorSnack
import com.example.inventorydashboard.presentation.viewModel.InventoryViewModel
import com.example.inventorydashboard.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: InventoryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: InventoryAdapter

    private var pendingScrollToTop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = InventoryAdapter()
        binding.recyclerInventory.apply {
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.etSearch.setText("")
            binding.spCategory.setSelection(0)
            pendingScrollToTop = true
            viewModel.refresh(isPulled = true)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lastSyncText.collect { text ->
                    binding.tvLastSync.text = text
                }
            }
        }

        binding.etSearch.addTextChangedListener { text ->
            val value = text?.toString().orEmpty()
            viewModel.search(value)
            if (value.isBlank()) pendingScrollToTop = true
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryOptions.collect { category ->
                    if (category.isNotEmpty()) {
                        val filter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item,
                            category
                        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
                        binding.spCategory.adapter = filter
                        binding.spCategory.setSelection(0, false)
                    }
                }
            }
        }

        binding.spCategory.onItemSelected { position, selected ->
            viewModel.setCategory(selected)
            pendingScrollToTop = (position == 0)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { result ->
                    when (result) {
                        is Result.Loading -> binding.swipeRefresh.isRefreshing = true
                        is Result.Success -> {
                            adapter.submitList(result.data) {
                                if (pendingScrollToTop) {
                                    scrollToTop()
                                    pendingScrollToTop = false
                                }
                            }
                            binding.swipeRefresh.isRefreshing = false
                            binding.emptyState.visibility = if (result.data.isEmpty()) View.VISIBLE else View.GONE
                        }

                        is Result.Error -> {
                            binding.progress.visibility = View.GONE
                            binding.swipeRefresh.isRefreshing = false
                            binding.root.showErrorSnack(
                                result.message
                            ) {
                                binding.etSearch.setText("")
                                binding.spCategory.setSelection(0)
                                pendingScrollToTop = true
                                viewModel.refresh(isPulled = true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun scrollToTop() {
        val recyclerView: RecyclerView = binding.recyclerInventory
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        recyclerView.post {
            layoutManager.scrollToPositionWithOffset(0, 0)
            recyclerView.post { layoutManager.scrollToPositionWithOffset(0, 0) }
        }
    }
}
