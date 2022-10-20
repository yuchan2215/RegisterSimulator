package xyz.miyayu.android.registersimulator.views.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.journeyapps.barcodescanner.BarcodeCallback
import xyz.miyayu.android.registersimulator.databinding.ReaderViewBinding

class ReaderView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var _binding: ReaderViewBinding? = null
    val binding get() = _binding!!

    init {
        val inflater = LayoutInflater.from(context)
        _binding = ReaderViewBinding.inflate(inflater, this, true)
    }

    fun setBarcodeCallback(barcodeCallback: BarcodeCallback) {
        binding.barcodeView.decodeContinuous(barcodeCallback)
    }

    fun onResume() {
        binding.barcodeView.resume()
    }

    fun onPause() {
        binding.barcodeView.pause()
    }
}
