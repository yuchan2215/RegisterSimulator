package xyz.miyayu.android.registersimulator.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import xyz.miyayu.android.registersimulator.feature.common.ui.view.ReaderView
import xyz.miyayu.android.registersimulator.register.databinding.RegisterTopFragmentBinding

class RegisterTopFragment : Fragment(R.layout.register_top_fragment), BarcodeCallback {
    private var _barcodeView: ReaderView? = null
    private val barcodeView get() = _barcodeView!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = RegisterTopFragmentBinding.bind(view)
        _barcodeView = binding.readerView
        binding.readerView.setBarcodeCallback(this)
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
        Log.i("BarcodeResult", result.toString())
        // TODO
    }
}
