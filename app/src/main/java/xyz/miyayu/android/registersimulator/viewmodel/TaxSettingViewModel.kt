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

    init {
        fetchTaxRates()
    }
}