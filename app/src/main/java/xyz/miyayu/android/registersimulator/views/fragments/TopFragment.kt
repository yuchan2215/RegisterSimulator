package xyz.miyayu.android.registersimulator.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.TopFragmentBinding
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository
import javax.inject.Inject

@AndroidEntryPoint
class TopFragment : Fragment(R.layout.top_fragment) {
    @Inject
    lateinit var taxRateRepository: TaxRateRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TopFragmentBinding.bind(view)
        binding.openRegisterButton.setOnClickListener {
            view.findNavController().navigate(TopFragmentDirections.openRegister())
        }
        binding.openSettingsButton.setOnClickListener {
            view.findNavController()
                .navigate(TopFragmentDirections.actionTopFragmentToSettingFragment())
        }
        lifecycleScope.launch(Dispatchers.IO) {
            taxRateRepository.init()
        }
    }
}
