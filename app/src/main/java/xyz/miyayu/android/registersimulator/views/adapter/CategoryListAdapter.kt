package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.ResourceService
import xyz.miyayu.android.registersimulator.databinding.CategorySettingItemBinding
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.model.entity.TaxRate.Companion.getPreview

abstract class CategoryListAdapter(private val resourceService: ResourceService) :
    ListAdapter<CategoryAndTaxRate, CategoryListAdapter.CategoryViewHolder>(DiffCallback) {
    abstract fun onItemClicked(item: CategoryAndTaxRate)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategorySettingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    inner class CategoryViewHolder(private val binding: CategorySettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryAndTaxRate) {
            binding.apply {
                val taxPreview = item.taxRate?.getPreview(resourceService) ?: ""
                categoryName.text = item.category.name
                categoryTaxPreview.text = taxPreview
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CategoryAndTaxRate>() {
            override fun areContentsTheSame(
                oldItem: CategoryAndTaxRate,
                newItem: CategoryAndTaxRate
            ) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: CategoryAndTaxRate, newItem: CategoryAndTaxRate) =
                oldItem.category.categoryId == newItem.category.categoryId
        }
    }
}
