package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategorySettingViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categoryList = categoryRepository.getCategoriesAndTaxRatesFlow().asLiveData()

    fun deleteItem(categoryAndTaxRate: CategoryAndTaxRate) {
        val category = categoryAndTaxRate.category
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.delete(category)
        }
    }
}
