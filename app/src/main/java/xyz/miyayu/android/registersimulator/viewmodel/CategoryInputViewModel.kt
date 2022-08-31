package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.repositories.CategoryRepository
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository
import xyz.miyayu.android.registersimulator.views.fragments.settings.CategoryInputFragmentArgs

class CategoryInputViewModel(navigationArgs: CategoryInputFragmentArgs) : ViewModel() {
    private val _category: MutableLiveData<Category> = MutableLiveData()
    val category: LiveData<Category> = _category

    private val _taxRates: MutableLiveData<List<TaxRate>> = MutableLiveData()
    val taxRates: LiveData<List<TaxRate>> = _taxRates

    val canSave: LiveData<Boolean> = _category.map {
        it?.name?.isNotEmpty() ?: false
    }

    private val categoryId = navigationArgs.categoryId
    private val isNewly = categoryId == -1

    init {
        fetchCategory()
        fetchTaxRates()
    }

    /**
     * カテゴリを読み込む。もしデフォルト値の-1なら新しく作成する。
     */
    private fun fetchCategory() {
        if (isNewly) {
            _category.value = Category(name = "", defaultTaxRateId = 1)
            return
        }
        viewModelScope.launch {
            _category.value = CategoryRepository.getCategory(categoryId)
        }
    }

    private fun fetchTaxRates() {
        viewModelScope.launch {
            _taxRates.value = TaxRateRepository.getTaxRates()
        }
    }

    fun setCategoryName(newName: String) {
        val copiedCategory = _category.value!!.copy(name = newName)
        _category.value = copiedCategory
    }

    fun setCategoryTaxRate(id: Int) {
        val copiedCategory = _category.value!!.copy(defaultTaxRateId = id)
        _category.value = copiedCategory
    }

    fun save() {
        viewModelScope.launch {
            if (isNewly) {
                CategoryRepository.insert(category.value!!)
            } else {
                CategoryRepository.update(category.value!!)
            }
        }
    }

    class Factory(private val navigationArgs: CategoryInputFragmentArgs) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryInputViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryInputViewModel(navigationArgs) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}