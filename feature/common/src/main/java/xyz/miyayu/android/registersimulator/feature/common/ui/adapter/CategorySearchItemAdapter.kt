package xyz.miyayu.android.registersimulator.feature.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.feature.common.databinding.CategorySearchItemBinding
import xyz.miyayu.android.registersimulator.model.CategoryDetail
import xyz.miyayu.android.registersimulator.model.TaxRate.Companion.getPreview
import xyz.miyayu.android.registersimulator.utils.ResourceService

internal abstract class CategorySearchItemAdapter(private val resourceService: ResourceService) :
    ListAdapter<CategoryDetail, CategorySearchItemAdapter.CategoryViewHolder>(
        DiffCallback
    ) {
    abstract fun onItemClicked(item: CategoryDetail)

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
        fun bind(item: CategoryDetail) {
            binding.apply {
                val taxPreview = item.taxRate?.getPreview(resourceService) ?: ""
                categoryName.text = item.category.name
                categoryTaxPreview.text = taxPreview
            }
        }
    }

    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<CategoryDetail>() {
                override fun areContentsTheSame(
                    oldItem: CategoryDetail,
                    newItem: CategoryDetail
                ) =
                    oldItem == newItem

                override fun areItemsTheSame(
                    oldItem: CategoryDetail,
                    newItem: CategoryDetail
                ) =
                    oldItem.category.categoryId == newItem.category.categoryId
            }
    }
}
