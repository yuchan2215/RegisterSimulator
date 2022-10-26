package xyz.miyayu.android.registersimulator.feature.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.feature.common.databinding.CategorySearchItemBinding
import xyz.miyayu.android.registersimulator.model.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.model.TaxRate.Companion.getPreview
import xyz.miyayu.android.registersimulator.utils.ResourceService

internal abstract class CategorySearchItemAdapter(private val resourceService: ResourceService) :
    ListAdapter<CategoryAndTaxRate, CategorySearchItemAdapter.CategoryViewHolder>(
        DiffCallback
    ) {
    abstract fun onItemClicked(item: CategoryAndTaxRate)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategorySearchItemBinding.inflate(
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

    inner class CategoryViewHolder(private val binding: CategorySearchItemBinding) :
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
        private val DiffCallback =
            object : DiffUtil.ItemCallback<CategoryAndTaxRate>() {
                override fun areContentsTheSame(
                    oldItem: CategoryAndTaxRate,
                    newItem: CategoryAndTaxRate
                ) =
                    oldItem == newItem

                override fun areItemsTheSame(
                    oldItem: CategoryAndTaxRate,
                    newItem: CategoryAndTaxRate
                ) =
                    oldItem.category.categoryId == newItem.category.categoryId
            }
    }
}
