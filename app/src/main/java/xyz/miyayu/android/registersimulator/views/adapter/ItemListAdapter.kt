package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.databinding.ItemSettingItemBinding
import xyz.miyayu.android.registersimulator.model.entity.ProductItemDetail

abstract class ItemListAdapter :
    ListAdapter<ProductItemDetail, ItemListAdapter.ItemViewHolder>(DiffCallback) {
    abstract fun onItemClicked(item: ProductItemDetail)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemSettingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class ItemViewHolder(private val binding: ItemSettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItemDetail) {
            binding.apply {
                itemTitle.text = item.item.itemName
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProductItemDetail>() {
            override fun areContentsTheSame(
                oldItem: ProductItemDetail,
                newItem: ProductItemDetail
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: ProductItemDetail,
                newItem: ProductItemDetail
            ): Boolean {
                return oldItem.item.id == newItem.item.id
            }
        }
    }
}
