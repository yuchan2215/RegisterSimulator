package xyz.miyayu.android.registersimulator.views.fragments.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.SingleReadFragmentBinding

class SingleReadFragment : Fragment(R.layout.single_read_fragment) {
    private var _binding: SingleReadFragmentBinding? = null
    val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = SingleReadFragmentBinding.bind(view)
        binding.readerView.setBarcodeCallback {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                savedStateHandleKey,
                it.text
            )
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.readerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.readerView.onPause()
    }

    companion object {
        val savedStateHandleKey = this::class.java.name
    }
}
