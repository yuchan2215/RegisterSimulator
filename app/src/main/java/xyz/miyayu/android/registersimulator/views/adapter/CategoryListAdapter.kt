package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.databinding.CategorySettingItemBinding
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate

abstract class CategoryListAdapter :
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

    class CategoryViewHolder(private val binding: CategorySettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryAndTaxRate) {
            binding.apply {
                categoryName.text = item.category.name
                //TODO 税率を表示させる
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