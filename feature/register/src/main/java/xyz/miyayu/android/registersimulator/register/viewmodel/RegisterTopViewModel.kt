package xyz.miyayu.android.registersimulator.register.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.ProductItemDetail
import xyz.miyayu.android.registersimulator.repository.ItemRepository
import javax.inject.Inject

@HiltViewModel
class RegisterTopViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    val lastLoadItem = MutableLiveData<ProductItemDetail?>(null)

    fun onLoadJanCode(jan: Long) {
        if (lastLoadItem.value?.item?.janCode == jan) return
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemRepository.getNewestItemFromJan(jan) ?: return@launch
            viewModelScope.launch(Dispatchers.Main) {
                Log.i(this::class.simpleName, item.toString())
                lastLoadItem.value = item
            }
        }
    }
}