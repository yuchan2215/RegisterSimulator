package xyz.miyayu.android.registersimulator.viewmodel

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.ResourceService
import xyz.miyayu.android.registersimulator.model.entity.ProductItem
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.model.entity.TaxRate.Companion.getPreview
import xyz.miyayu.android.registersimulator.model.entity.price.Price.Companion.getFormattedString
import xyz.miyayu.android.registersimulator.model.entity.price.TaxIncludedPrice
import xyz.miyayu.android.registersimulator.model.entity.price.TaxPrice
import xyz.miyayu.android.registersimulator.model.entity.price.WithoutTaxPrice
import xyz.miyayu.android.registersimulator.model.entity.price.WithoutTaxPrice.Companion.convertToWithOutTaxPrice
import xyz.miyayu.android.registersimulator.repositories.CategoryRepository
import xyz.miyayu.android.registersimulator.repositories.ItemRepository
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository
import xyz.miyayu.android.registersimulator.views.fragments.settings.ItemAddFragmentArgs
import java.util.Date

class ItemAddViewModel @AssistedInject constructor(
    private val itemRepository: ItemRepository,
    private val taxRateRepository: TaxRateRepository,
    private val categoryRepository: CategoryRepository,
    private val resourceService: ResourceService,
    @Assisted private val navigationArgs: ItemAddFragmentArgs
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(navigationArgs: ItemAddFragmentArgs): ItemAddViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            navigationArgs: ItemAddFragmentArgs
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(navigationArgs) as T
            }
        }
    }

    val inputJanCode = MutableLiveData<String?>(null)
    val inputItemName = MutableLiveData<String?>(null)
    val inputPrice = MutableLiveData<String?>(null)
    private val selectedCategoryId = MutableLiveData<Int?>(null)
    private val selectedTaxRateId = MutableLiveData<Int?>(null)

    fun setSelectedTaxRateId(taxRateId: Int?) {
        selectedTaxRateId.value = taxRateId
    }

    fun setSelectedCategoryId(categoryId: Int?) {
        selectedCategoryId.value = categoryId
    }

    val priceWithOutTax = inputPrice.map {
        return@map WithoutTaxPrice(it)
    }

    val categoryDetails = selectedCategoryId.switchMap {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            if (it == null) {
                emit(null)
                return@liveData
            }
            val result = categoryRepository.getCategoryAndTaxRate(it)
            emit(result)
        }
    }

    // カテゴリ標準の税率
    val categoryTaxRatePreview = categoryDetails.map {
        if (it?.taxRate == null) return@map ""
        val taxRate = it.taxRate
        return@map taxRate.getPreview(resourceService)
    }

    inner class TaxRateModel(val taxRate: TaxRate?) {
        /**税率名*/
        val name: String = taxRate?.title ?: resourceService.getResources()
            .getString(R.string.tax_rate_not_set_display)

        /**税率のプレビュー*/
        val preview: String? = taxRate?.getPreview(resourceService)

        /**選択されている税率を表示するかどうか*/
        val selectedTaxRateVisibility = if (preview == null) View.GONE else View.VISIBLE
    }

    // 選択されている税率
    val selectedTaxRate = selectedTaxRateId.switchMap {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            if (it == null) {
                emit(TaxRateModel(null))
            } else {
                val result = taxRateRepository.getTaxRate(it)
                emit(TaxRateModel(result))
            }
        }
    }

    /**
     * 現在の税率。
     * 税率がマニュアルで設定されていればそちらを利用し、設定されていなければカテゴリ標準を利用する。
     * ただし、どちらもnullなら0とする。
     */
    private val currentTaxRate by lazy {
        object : MediatorLiveData<TaxRate?>() {
            val observer = Observer<Any?> {
                val selectedTaxRate = selectedTaxRate.value?.taxRate
                val selectedCategoryTaxRate = categoryDetails.value?.taxRate
                this.value = selectedTaxRate ?: selectedCategoryTaxRate
            }
        }.apply {
            this.value = null
            addSource(categoryDetails, observer)
            addSource(selectedTaxRate, observer)
        }
    }

    private val taxPrice by lazy {
        object : MediatorLiveData<TaxPrice?>() {
            val observer = Observer<Any?> {
                val price = priceWithOutTax.value
                val taxRate = currentTaxRate.value
                this.value = TaxPrice(price, taxRate)
            }
        }.apply {
            this.value = null
            addSource(currentTaxRate, observer)
            addSource(priceWithOutTax, observer)
        }
    }

    val taxPriceString = taxPrice.map { taxPrice ->
        val tax = taxPrice.getFormattedString()
        resourceService.getResources().getString(R.string.tax_preview, tax)
    }

    private val taxIncludedPrice by lazy {
        object : MediatorLiveData<TaxIncludedPrice?>() {
            val observer = Observer<Any?> {
                val taxPrice = priceWithOutTax.value
                val taxRate = currentTaxRate.value
                this.value = TaxIncludedPrice(taxPrice, taxRate)
            }
        }.apply {
            this.value = null
            addSource(taxPrice, observer)
            addSource(priceWithOutTax, observer)
        }
    }

    val taxIncludedPriceString = taxIncludedPrice.map { taxIncludedPrice ->
        val sum = taxIncludedPrice.getFormattedString()
        return@map resourceService.getResources().getString(R.string.price_preview, sum)
    }

    val canSave by lazy {
        object : MediatorLiveData<Boolean?>() {
            val observer = Observer<Any?> {
                val itemNameValidate = !inputItemName.value.isNullOrEmpty()
                val itemPriceValidate = !inputPrice.value.isNullOrEmpty()
                val categoryIdValidate = selectedCategoryId.value != null

                this.value = itemNameValidate && itemPriceValidate && categoryIdValidate
            }
        }.apply {
            this.value = null
            addSource(inputItemName, observer)
            addSource(inputPrice, observer)
            addSource(selectedCategoryId, observer)
        }
    }

    @Throws(IllegalStateException::class)
    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.addItem(
                itemId = oldItem?.id,
                janCode = inputJanCode.value?.toLongOrNull(),
                itemName = inputItemName.value ?: throw IllegalStateException("商品名が入力されていません"),
                price = inputPrice.value?.toBigDecimalOrNull()?.convertToWithOutTaxPrice()
                    ?: throw IllegalStateException("価格が不正です"),
                categoryId = selectedCategoryId.value
                    ?: throw IllegalStateException("カテゴリが選択されていません"),
                taxRateId = selectedTaxRateId.value,
                makeDate = oldItem?.makeDate ?: Date()
            )
        }
    }

    var oldItem: ProductItem? = null
    private val isLoaded = MutableLiveData<Boolean>(false)
    fun load() {
        if (isLoaded.value == true) return
        isLoaded.value = true
        val id = navigationArgs.itemId
        if (id == -1) return
        viewModelScope.launch(Dispatchers.Main) {
            val item = withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                itemRepository.getItem(id)
            }
            inputJanCode.value = item.janCode?.toString() ?: ""
            inputItemName.value = item.itemName
            inputPrice.value = item.price.toString()
            selectedCategoryId.value = item.categoryId
            selectedTaxRateId.value = item.taxId
            oldItem = item
        }
    }
}
