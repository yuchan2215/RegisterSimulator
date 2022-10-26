package xyz.miyayu.android.registersimulator.feature.setting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.model.TaxRate
import xyz.miyayu.android.registersimulator.repository.TaxRateRepository
import javax.inject.Inject

@HiltViewModel
class TaxSettingViewModel @Inject constructor(
    private val taxRateRepository: TaxRateRepository
) : ViewModel() {
    private val _taxRates: MutableLiveData<List<TaxRate>?> = MutableLiveData(null)
    val taxRates: LiveData<List<TaxRate>?> = _taxRates

    private fun fetchTaxRates() {
        viewModelScope.launch(Dispatchers.Main) {
            _taxRates.value = taxRateRepository.getTaxRates()
        }
    }

    fun saveTaxRates() {
        taxRates.value?.also {
            viewModelScope.launch(Dispatchers.IO) {
                taxRateRepository.saveTaxRates(it)
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
