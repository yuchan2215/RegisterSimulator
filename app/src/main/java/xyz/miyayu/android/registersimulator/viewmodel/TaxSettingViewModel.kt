package xyz.miyayu.android.registersimulator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository

class TaxSettingViewModel : ViewModel() {
    private val _taxRates: MutableLiveData<List<TaxRate>?> = MutableLiveData(null)
    val taxRates: LiveData<List<TaxRate>?> = _taxRates

    private fun fetchTaxRates() {
        viewModelScope.launch(Dispatchers.Main) {
            _taxRates.value = TaxRateRepository.getTaxRates()
        }
    }

    fun saveTaxRates() {
        taxRates.value?.also {
            viewModelScope.launch(Dispatchers.IO) {
                TaxRateRepository.saveTaxRates(it)
            }
        }
    }

    fun setTaxTitle(editId: Int, title: String) {
        _taxRates.value = _taxRates.value?.map {
            if (editId == it.id) {
                it.copy(title = title)
            } else {
                it
            }
        }
    }

    fun setTaxRate(editId: Int, taxRate: String) {
        _taxRates.value = _taxRates.value?.map {
            if (editId == it.id) {
                it.copy(rate = taxRate)
            } else {
                it
            }
        }
    }

    init {
        fetchTaxRates()
    }
}