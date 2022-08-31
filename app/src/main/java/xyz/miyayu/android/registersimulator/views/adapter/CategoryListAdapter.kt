package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.databinding.CategorySettingItemBinding
import xyz.miyayu.android.registersimulator.model.entity.Category

abstract class CategoryListAdapter :
    ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(DiffCallback) {
    abstract fun onItemClicked(category: Category)

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
        fun bind(category: Category) {
            binding.apply {
                categoryName.text = category.name
                //TODO 税率を表示させる
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.categoryId == newItem.categoryId
        }
    }
}