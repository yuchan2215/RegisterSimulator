package xyz.miyayu.android.registersimulator.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.TopFragmentBinding

class TopFragment : Fragment(R.layout.top_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TopFragmentBinding.bind(view)
        binding.openRegisterButton.setOnClickListener {
            view.findNavController().navigate(TopFragmentDirections.openRegister())
        }
        binding.openSettingsButton.setOnClickListener {
            view.findNavController()
                .navigate(TopFragmentDirections.actionTopFragmentToSettingFragment())
        }
    }
}