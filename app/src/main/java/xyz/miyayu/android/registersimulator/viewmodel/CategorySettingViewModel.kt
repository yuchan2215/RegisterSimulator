package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import xyz.miyayu.android.registersimulator.repositories.CategoryRepository

class CategorySettingViewModel : ViewModel() {
    val categoryList = CategoryRepository.getCategoriesFlow().asLiveData()
}