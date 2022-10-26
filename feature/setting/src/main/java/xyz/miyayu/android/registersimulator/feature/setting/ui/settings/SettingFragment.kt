package xyz.miyayu.android.registersimulator.feature.setting.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import xyz.miyayu.android.registersimulator.feature.setting.R
import xyz.miyayu.android.registersimulator.feature.setting.databinding.SettingFragmentBinding

class SettingFragment : Fragment(R.layout.setting_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SettingFragmentBinding.bind(view).apply {
            taxSetting.setOnClickListener {
                view.findNavController().navigate(SettingFragmentDirections.openTaxSetting())
            }
            storeCategories.setOnClickListener {
                view.findNavController().navigate(SettingFragmentDirections.openCategorySetting())
            }
            storeItems.setOnClickListener {
                view.findNavController().navigate(SettingFragmentDirections.openItemList())
            }
        }
    }
}
