package xyz.miyayu.android.registersimulator.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.miyayu.android.registersimulator.repository.ItemRepository
import javax.inject.Inject

@HiltViewModel
internal class ItemListViewModel @Inject constructor(
    itemRepository: ItemRepository
) : ViewModel() {
    val allItems = itemRepository.getAllItemDetailsFlow().asLiveData()
}
