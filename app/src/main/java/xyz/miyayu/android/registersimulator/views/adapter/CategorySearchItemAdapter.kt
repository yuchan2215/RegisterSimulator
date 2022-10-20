package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategorySearchItemBinding
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate

abstract class CategorySearchItemAdapter() :
    ListAdapter<CategoryAndTaxRate, CategorySearchItemAdapter.CategoryViewHolder>(DiffCallback) {
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

    class CategoryViewHolder(private val binding: CategorySearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryAndTaxRate) {
            binding.apply {
                val taxPreview = item.taxRate?.let {
                    this.root.context.getString(
                        R.string.category_tax_preview,
                        it.title,
                        it.rate
                    )
                } ?: "NULL"
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
