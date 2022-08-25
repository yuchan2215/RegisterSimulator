package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.TaxSettingFragmentBinding
import xyz.miyayu.android.registersimulator.util.SimpleTextWatcher
import xyz.miyayu.android.registersimulator.viewmodel.TaxSettingViewModel

class TaxSettingFragment : Fragment(R.layout.tax_setting_fragment) {
    private val taxSettingViewModel: TaxSettingViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TaxSettingFragmentBinding.bind(view)
        val settingItems = listOf(binding.taxSetting1, binding.taxSetting2, binding.taxSetting3)
        taxSettingViewModel.taxRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            it.forEachIndexed { index, taxRate ->
                //ViewModelから受け取った値をViewに反映させる
                val settingItem = settingItems[index]
                settingItem.setTitle(taxRate.title)
                settingItem.setRate(taxRate.rate)
                settingItem.setLabel(getString(R.string.tax_rate_label, index + 1))
                //読み込みが終わったので見えるようにする
                settingItem.visibility = View.VISIBLE

                //リスナーを作成し、ViewModelに反映させる
                val titleTextChangedListener = object : SimpleTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        taxSettingViewModel.setTaxTitle(index, s.toString())
                    }
                }
                val taxTextChangedListener = object : SimpleTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        taxSettingViewModel.setTaxRate(index, s.toString())
                    }
                }
                settingItem.setTitleTextChangedListener(titleTextChangedListener)
                settingItem.setRateTextChangedListener(taxTextChangedListener)
            }
            // View側から最新情報を書き込むので、オブザーブは解除する。
            taxSettingViewModel.taxRates.removeObservers(viewLifecycleOwner)
        }
    }
}