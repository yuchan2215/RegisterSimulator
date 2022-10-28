package xyz.miyayu.android.registersimulator.feature.common.ui.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.client.android.DecodeFormatManager
import com.google.zxing.client.android.DecodeHintManager
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import xyz.miyayu.android.registersimulator.feature.common.R
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

    fun onViewCreated(activity: Activity) {
        if (!isAllowCamera()) {
            showCameraAllowDialog(activity)
        }
    }

    fun onPause() {
        binding.barcodeView.pause()
    }

    private fun isAllowCamera(): Boolean {
        val code = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        return code == PackageManager.PERMISSION_GRANTED
    }

    private fun showCameraAllowDialog(activity: Activity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.CAMERA
            ) || isFirstPermissionCheck(sharedPref)
        ) {
            //権限チェックが表示できる状態なら
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 1)
        } else {
            //権限チャックが表示できない状態なら
            AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.please_allow_camera_permission_message))
                .setPositiveButton(R.string.open_setting) { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)

                }.create().show()
        }
        //一度チェックしたことを保存しておく。
        saveCheckedState(sharedPref)
    }

    private fun isFirstPermissionCheck(pref: SharedPreferences): Boolean {
        return pref.getBoolean(IS_FIRST, true)
    }

    private fun saveCheckedState(pref: SharedPreferences) {
        with(pref.edit()) {
            putBoolean(IS_FIRST, false)
            apply()
        }
    }

    companion object {
        private const val IS_FIRST = "IS_FIRST"
    }
}
