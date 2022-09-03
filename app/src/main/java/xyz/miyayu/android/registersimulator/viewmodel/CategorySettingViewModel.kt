package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.repositories.CategoryRepository

class CategorySettingViewModel : ViewModel() {
    val categoryList = CategoryRepository.getCategoriesAndTaxRatesFlow().asLiveData()

    fun deleteItem(categoryAndTaxRate: CategoryAndTaxRate) {
        val category = categoryAndTaxRate.category
        viewModelScope.launch(Dispatchers.IO) {
            CategoryRepository.delete(category)
        }
    }
}
