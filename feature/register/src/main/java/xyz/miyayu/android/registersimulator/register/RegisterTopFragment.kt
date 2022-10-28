package xyz.miyayu.android.registersimulator.register

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.common.ui.view.ReaderView
import xyz.miyayu.android.registersimulator.model.price.TaxIncludedPrice.Companion.getPreview
import xyz.miyayu.android.registersimulator.register.databinding.RegisterTopFragmentBinding
import xyz.miyayu.android.registersimulator.register.viewmodel.RegisterTopViewModel
import xyz.miyayu.android.registersimulator.utils.ResourceService
import javax.inject.Inject

@AndroidEntryPoint
class RegisterTopFragment : Fragment(R.layout.register_top_fragment), BarcodeCallback {
    private var _barcodeView: ReaderView? = null
    private val barcodeView get() = _barcodeView!!

    private val viewModel: RegisterTopViewModel by viewModels()

    @Inject
    lateinit var resourceService: ResourceService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = RegisterTopFragmentBinding.bind(view)
        _barcodeView = binding.readerView
        binding.readerView.setBarcodeCallback(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.composeView.setContent {
            val item = viewModel.lastLoadItem.observeAsState()
            val itemName = item.value?.item?.itemName ?: ""
            val price =
                item.value?.getTaxIncludedPrice().getPreview(resourceService)
            val categoryName = item.value?.defaultCategoryDetail?.category?.name ?: ""
            DetailView(itemName, price, categoryName)
        }
        barcodeView.onViewCreated(requireActivity())
    }

    @Preview(showBackground = true, widthDp = 300)
    @Composable
    private fun DetailViewPreview() {
        MaterialTheme {
            DetailView(itemName = "いくら", taxIncludedPrice = "1,980円", categoryName = "食品")
        }
    }

    @Composable
    private fun DetailView(itemName: String, taxIncludedPrice: String, categoryName: String) {

        MaterialTheme {
            Column {
                Text(
                    itemName,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    categoryName,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    taxIncludedPrice,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }

    @Composable
    private fun ListItem(title: String, content: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primaryVariant,

                )
        }
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
