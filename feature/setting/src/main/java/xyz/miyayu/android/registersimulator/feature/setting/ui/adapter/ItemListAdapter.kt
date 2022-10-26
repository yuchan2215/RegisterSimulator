package xyz.miyayu.android.registersimulator.feature.setting.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.feature.setting.R
import xyz.miyayu.android.registersimulator.feature.setting.databinding.ItemSettingItemBinding
import xyz.miyayu.android.registersimulator.model.ProductItemDetail
import xyz.miyayu.android.registersimulator.model.price.TaxIncludedPrice.Companion.getTaxIncludedPricePreviewString
import xyz.miyayu.android.registersimulator.model.price.WithoutTaxPrice.Companion.getWithOutTaxPreviewString
import xyz.miyayu.android.registersimulator.utils.ResourceService

internal abstract class ItemListAdapter(private val resourceService: ResourceService) :
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
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
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
                price.text = item.item.price.getWithOutTaxPreviewString(resourceService)

                priceWithTax.text = item.getTaxIncludedPrice().getTaxIncludedPricePreviewString(resourceService)

                errorText.isVisible = item.taxRate != null
                errorText.text =
                    resourceService.getResources().getString(R.string.category_override_message)
            }
        }
    }

    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<ProductItemDetail>() {
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
