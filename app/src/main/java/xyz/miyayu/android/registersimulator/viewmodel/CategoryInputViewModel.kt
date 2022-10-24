package xyz.miyayu.android.registersimulator.viewmodel

import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.ResourceService
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.repositories.CategoryRepository
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository
import xyz.miyayu.android.registersimulator.views.fragments.settings.CategoryInputFragmentArgs

class CategoryInputViewModel @AssistedInject constructor(
    private val taxRateRepository: TaxRateRepository,
    private val categoryRepository: CategoryRepository,
    private val resourceService: ResourceService,
    @Assisted navigationArgs: CategoryInputFragmentArgs
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(navigationArgs: CategoryInputFragmentArgs): CategoryInputViewModel
    }

    companion object {
        const val DEFAULT_TAX_RATE_ID = 1
        fun provideFactory(
            assistedFactory: Factory,
            navigationArgs: CategoryInputFragmentArgs
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(navigationArgs) as T
            }
        }
    }

    val categoryName = MutableLiveData<String>(null)
    val choseTaxRateLayoutId = MutableLiveData<Int>(null)

    private val _taxRates: MutableLiveData<List<TaxRate>> = MutableLiveData()

    /**
     * フォーマットされた税率のリスト
     */
    val taxRatePreviews: LiveData<List<String>> = _taxRates.map { taxRates ->
        return@map listOf(
            taxRates?.getOrNull(0),
            taxRates?.getOrNull(1),
            taxRates?.getOrNull(2)
        ).map { taxRate ->
            // Nullなら空欄
            if (taxRate == null) ""
            else resourceService.getResources().getString(
                R.string.category_tax_preview,
                taxRate.title,
                taxRate.rate
            )
        }
    }

    val onCheckedChangeListener = OnCheckedChangeListener { group, _ ->
        if (isInitialized.value == false) {
            group?.jumpDrawablesToCurrentState()
        }
    }

    /**
     * 初期化の検証。
     */
    val isInitialized = MediatorLiveData<Boolean>().apply {
        this.value = false

        val viewModels = listOf(_taxRates, categoryName, choseTaxRateLayoutId)

        val changedObserver = Observer<Any> {
            val nameValidate = categoryName.value != null
            val choseValidate = choseTaxRateLayoutId.value != null
            val taxRateValidate = _taxRates.value != null
            this.value = nameValidate && taxRateValidate && choseValidate

            // もしTrueになったらソースを削除する。
            if (this.value == true) {
                viewModels.forEach {
                    removeSource(it)
                }
            }
        }
        // ソース追加
        viewModels.forEach {
            addSource(it, changedObserver)
        }
    }

    /**
     * 入力内容の検証。
     * カテゴリ名が空ではない　かつ　税率が設定されていることを確認する。
     */
    val isValidated = MediatorLiveData<Boolean>().apply {
        this.value = false

        val changedObserver = Observer<Any> {
            val nameValidate = !categoryName.value.isNullOrEmpty()
            val taxChoiceValidate = choseTaxRateLayoutId.value != null
            this.value = nameValidate && taxChoiceValidate
        }
        addSource(_taxRates, changedObserver)
        addSource(categoryName, changedObserver)
    }

    /**
     * カテゴリID。
     * もし初期値である-1が入っているならNullとする。
     */
    private val categoryId = navigationArgs.categoryId.let {
        if (it == -1) null
        else it
    }

    init {
        fetchCategory()
        fetchTaxRates()
    }

    /**
     * カテゴリを読み込む。もしデフォルト値の-1なら新しく作成する。
     */
    private fun fetchCategory() {
        if (categoryId == null) {
            categoryName.value = ""
            choseTaxRateLayoutId.value = getTaxRateLayoutId(DEFAULT_TAX_RATE_ID)
            return
        }
        viewModelScope.launch {
            val category = categoryRepository.getCategory(categoryId)
            categoryName.value = category.name
            choseTaxRateLayoutId.value =
                getTaxRateLayoutId(category.defaultTaxRateId)
        }
    }

    private fun fetchTaxRates() {
        viewModelScope.launch {
            _taxRates.value = taxRateRepository.getTaxRates()
        }
    }

    fun save() {
        val safeCategoryName = categoryName.value!!
        val safeCategoryDefaultTaxRateId = getTaxRateId(choseTaxRateLayoutId.value)
        val category = Category(categoryId, safeCategoryName, safeCategoryDefaultTaxRateId)
        viewModelScope.launch {
            if (categoryId == null) {
                categoryRepository.insert(category)
            } else {
                categoryRepository.update(category)
            }
        }
    }

    /**
     * レイアウトIDから税率IDを取得する
     */
    private fun getTaxRateId(
        taxRateLayoutId: Int?,
    ): Int {
        return when (taxRateLayoutId) {
            R.id.category_tax_1 -> 1
            R.id.category_tax_2 -> 2
            R.id.category_tax_3 -> 3
            else -> throw IllegalStateException()
        }
    }

    /**
     * 税率IDからレイアウトIDを取得する
     */
    private fun getTaxRateLayoutId(taxRateId: Int?): Int {
        return when (taxRateId) {
            1 -> R.id.category_tax_1
            2 -> R.id.category_tax_2
            3 -> R.id.category_tax_3
            else -> throw IllegalStateException()
        }
    }
}
