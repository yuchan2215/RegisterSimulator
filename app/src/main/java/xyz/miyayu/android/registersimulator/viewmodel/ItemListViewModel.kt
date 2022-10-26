package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.miyayu.android.registersimulator.repository.ItemRepository
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    val allItems = itemRepository.getAllItemDetailsFlow().asLiveData()
}
