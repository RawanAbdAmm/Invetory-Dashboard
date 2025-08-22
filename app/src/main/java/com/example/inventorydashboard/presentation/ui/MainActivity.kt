package com.example.inventorydashboard.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorydashboard.databinding.ActivityMainBinding
import com.example.inventorydashboard.presentation.adapter.InventoryAdapter
import com.example.inventorydashboard.presentation.viewModel.InventoryViewModel
import com.example.inventorydashboard.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: InventoryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: InventoryAdapter

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



        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { result ->
                    when (result) {
                        is Result.Loading -> binding.swipeRefresh.isRefreshing = true
                        is Result.Success -> {
                            binding.swipeRefresh.isRefreshing = false
                            adapter.submitList(result.data)
                        }
                        is Result.Error -> {
                            binding.swipeRefresh.isRefreshing = false
                            adapter.submitList(emptyList())
                        }
                    }
                }
            }
        }
    }
}
