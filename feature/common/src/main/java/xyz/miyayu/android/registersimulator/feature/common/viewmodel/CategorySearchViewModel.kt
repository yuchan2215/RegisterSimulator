package xyz.miyayu.android.registersimulator.feature.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.miyayu.android.registersimulator.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategorySearchViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categoryList = categoryRepository.getCategoriesAndTaxRatesFlow().asLiveData()
}
