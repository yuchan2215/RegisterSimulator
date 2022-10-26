package xyz.miyayu.android.registersimulator.feature.setting.ui.settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.setting.R
import xyz.miyayu.android.registersimulator.feature.setting.databinding.TaxSettingFragmentBinding
import xyz.miyayu.android.registersimulator.feature.setting.viewmodel.TaxSettingViewModel
import xyz.miyayu.android.registersimulator.utils.SimpleTextWatcher

@AndroidEntryPoint
class TaxSettingFragment : Fragment(R.layout.tax_setting_fragment) {
    private val taxSettingViewModel: TaxSettingViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TaxSettingFragmentBinding.bind(view)
        val settingItems = listOf(binding.taxSetting1, binding.taxSetting2, binding.taxSetting3)
        taxSettingViewModel.taxRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            it.forEachIndexed { index, taxRate ->
                // ViewModelから受け取った値をViewに反映させる
                val settingItem = settingItems[index]
                settingItem.setTitle(taxRate.title)
                settingItem.setRate(taxRate.rate)
                settingItem.setLabel(getString(R.string.tax_rate_label, index + 1))
                // 読み込みが終わったので見えるようにする
                settingItem.visibility = View.VISIBLE

                // リスナーを作成し、ViewModelに反映させる
                val titleTextChangedListener = object : SimpleTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        taxSettingViewModel.setTaxTitle(taxRate.id, s.toString())
                    }
                }
                val taxTextChangedListener = object : SimpleTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        taxSettingViewModel.setTaxRate(taxRate.id, s.toString())
                    }
                }
                settingItem.setTitleTextChangedListener(titleTextChangedListener)
                settingItem.setRateTextChangedListener(taxTextChangedListener)
            }
            // View側から最新情報を書き込むので、オブザーブは解除する。
            taxSettingViewModel.taxRates.removeObservers(viewLifecycleOwner)
        }
        binding.floatingSaveButton.setOnClickListener {
            hideKeyboard()
            taxSettingViewModel.saveTaxRates()
            Snackbar.make(it, R.string.saved_message, Snackbar.LENGTH_LONG).show()
        }
    }

    // 参考：https://qiita.com/bassaer/items/0e412d9f36b2113ee8d0
    private fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val manager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
