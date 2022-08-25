package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.TaxSettingFragmentBinding
import xyz.miyayu.android.registersimulator.viewmodel.TaxSettingViewModel

class TaxSettingFragment : Fragment(R.layout.tax_setting_fragment) {
    private val taxSettingViewModel: TaxSettingViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TaxSettingFragmentBinding.bind(view)
        val settingItems = listOf(binding.taxSetting1, binding.taxSetting2, binding.taxSetting3)
        taxSettingViewModel.taxRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            it.forEachIndexed { index, taxRate ->
                val settingItem = settingItems[index]
                settingItem.setTitle(taxRate.title)
                settingItem.setRate(taxRate.rate)
                settingItem.setLabel(getString(R.string.tax_rate_label, index + 1))
                settingItem.visibility = View.VISIBLE
            }
            // View側から最新情報を書き込むので、オブザーブは解除する。
            taxSettingViewModel.taxRates.removeObservers(viewLifecycleOwner)
        }
    }
}