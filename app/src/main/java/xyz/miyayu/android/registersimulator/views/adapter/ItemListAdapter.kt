package xyz.miyayu.android.registersimulator.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.ItemSettingItemBinding
import xyz.miyayu.android.registersimulator.model.entity.ProductItemDetail
import xyz.miyayu.android.registersimulator.util.DecimalUtils.getSplitString
import xyz.miyayu.android.registersimulator.util.ResourceService

abstract class ItemListAdapter(private val resourceService: ResourceService) :
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

                janCode.isVisible = item.item.janCode != null
                janCode.text = item.item.janCode.toString()

                categoryName.text = item.defaultCategoryDetail.category.name
                price.text = resourceService.getResources().getString(
                    R.string.without_tax_price,
                    item.item.getBigDecimalPrice().getSplitString()
                )
                priceWithTax.text = resourceService.getResources().getString(
                    R.string.price_preview,
                    item.getTaxIncludedPrice().getSplitString()
                )
                errorText.isVisible = item.taxRate != null
                errorText.text =
                    resourceService.getResources().getString(R.string.category_override_message)
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
