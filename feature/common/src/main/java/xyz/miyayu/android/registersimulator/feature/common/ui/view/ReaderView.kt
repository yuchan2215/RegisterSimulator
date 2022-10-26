package xyz.miyayu.android.registersimulator.feature.common.ui.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.zxing.client.android.DecodeFormatManager
import com.google.zxing.client.android.DecodeHintManager
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import xyz.miyayu.android.registersimulator.feature.common.databinding.ReaderViewBinding

/**
 * バーコードリーダーのView。レイアウト等を一括管理するためにViewとして管理する。
 */
class ReaderView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var _binding: ReaderViewBinding? = null
    val binding get() = _binding!!

    init {
        val inflater = LayoutInflater.from(context)
        _binding = ReaderViewBinding.inflate(inflater, this, true)
    }

    fun setBarcodeCallback(barcodeCallback: BarcodeCallback) {
        val intent = Intent().apply {
            // バーコードのみ読み込めるようにする。
            putExtra(Intents.Scan.MODE, Intents.Scan.PRODUCT_MODE)
        }
        val decodeFormats = DecodeFormatManager.parseDecodeFormats(intent)
        val decodeHints = DecodeHintManager.parseDecodeHints(intent)
        binding.barcodeView.barcodeView.decoderFactory =
            DefaultDecoderFactory(decodeFormats, decodeHints, null, 0)
        binding.barcodeView.decodeContinuous(barcodeCallback)
    }

    fun onResume() {
        binding.barcodeView.resume()
    }

    fun onPause() {
        binding.barcodeView.pause()
    }
}
