package com.example.inventorydashboard.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorydashboard.databinding.ItemInventoryCardBinding
import com.example.inventorydashboard.domain.model.CombinedItem
import java.text.NumberFormat
import java.util.Locale

class InventoryAdapter :
    ListAdapter<CombinedItem, InventoryAdapter.ViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<CombinedItem>() {
        override fun areItemsTheSame(oldItem: CombinedItem, newItem: CombinedItem): Boolean =
            oldItem.itemNo == newItem.itemNo

        override fun areContentsTheSame(oldItem: CombinedItem, newItem: CombinedItem): Boolean =
            oldItem == newItem
    }

    class ViewHolder(val binding: ItemInventoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemInventoryCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val quantityFormat = NumberFormat.getNumberInstance(Locale.getDefault())

        holder.binding.tvName.text = item.name
        holder.binding.tvCategory.text = item.category ?: "—"
        holder.binding.tvQty.text = quantityFormat.format(item.quantity)
    }
}
