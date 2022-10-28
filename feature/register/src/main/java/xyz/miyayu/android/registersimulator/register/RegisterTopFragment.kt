package xyz.miyayu.android.registersimulator.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.common.ui.view.ReaderView
import xyz.miyayu.android.registersimulator.register.databinding.RegisterTopFragmentBinding
import xyz.miyayu.android.registersimulator.register.viewmodel.RegisterTopViewModel

@AndroidEntryPoint
class RegisterTopFragment : Fragment(R.layout.register_top_fragment), BarcodeCallback {
    private var _barcodeView: ReaderView? = null
    private val barcodeView get() = _barcodeView!!

    private val viewModel: RegisterTopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = RegisterTopFragmentBinding.bind(view)
        _barcodeView = binding.readerView
        binding.readerView.setBarcodeCallback(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        barcodeView.onViewCreated(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        barcodeView.onResume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _barcodeView = null
    }

    override fun barcodeResult(result: BarcodeResult?) {
        if (result == null) return
        viewModel.onLoadJanCode(result.result.text.toLong())
    }
}
