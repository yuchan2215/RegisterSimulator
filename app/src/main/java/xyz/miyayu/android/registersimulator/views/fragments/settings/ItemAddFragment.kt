package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.TaxRate
import xyz.miyayu.android.registersimulator.TaxRateRepository
import xyz.miyayu.android.registersimulator.databinding.ItemAddFragmentBinding
import xyz.miyayu.android.registersimulator.viewmodel.ItemAddViewModel
import xyz.miyayu.android.registersimulator.views.fragments.utils.CategorySelectFragment
import xyz.miyayu.android.registersimulator.views.fragments.utils.SingleReadFragment
import javax.inject.Inject

@AndroidEntryPoint
class ItemAddFragment : Fragment(R.layout.item_add_fragment) {
    @Inject
    lateinit var viewModelFactory: ItemAddViewModel.Factory

    @Inject
    lateinit var taxRateRepository: TaxRateRepository

    private val navigationArgs: ItemAddFragmentArgs by navArgs()
    private val viewModel: ItemAddViewModel by viewModels(
        ownerProducer = { this },
        factoryProducer = {
            val args = navigationArgs
            ItemAddViewModel.provideFactory(viewModelFactory, args)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ItemAddFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.load()
        binding.selectTaxButton.setOnClickListener {
            /**[TaxSelectFragment]を開く。*/
            lifecycleScope.launch(Dispatchers.Main) {
                val taxRates = withContext(lifecycleScope.coroutineContext + Dispatchers.IO) {
                    taxRateRepository.getTaxRates()
                }.toTypedArray()
                findNavController().navigate(
                    ItemAddFragmentDirections.actionItemAddFragmentToTaxSelectFragment(
                        taxRates,
                        viewModel.selectedTaxRate.value?.taxRate
                    )
                )
            }
        }
        binding.selectCategoryButton.setOnClickListener {
            findNavController().navigate(
                ItemAddFragmentDirections.actionItemAddFragmentToCategorySearchFragment()
            )
        }
        binding.importJanButton.setOnClickListener {
            findNavController().navigate(
                ItemAddFragmentDirections.actionItemAddFragmentToSingleReadFragment()
            )
        }
        // 税率選択を受け取る。
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<TaxRate?>(
            TaxSelectFragment.savedStateHandleKey
        )?.observe(viewLifecycleOwner) {
            viewModel.setSelectedTaxRateId(it?.id)
        }
        // カテゴリ選択を受け取る
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int?>(
            CategorySelectFragment.savedStateHandleKey
        )?.observe(viewLifecycleOwner) {
            viewModel.setSelectedCategoryId(it)
        }
        // JANコードを受け取る
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String?>(
            SingleReadFragment.savedStateHandleKey
        )?.observe(viewLifecycleOwner) {
            Log.d("Catch Qr Result!", it.toString())
            viewModel.inputJanCode.value = it
        }
        binding.saveButton.setOnClickListener {
            try {
                viewModel.save()
                findNavController().popBackStack()
            } catch (_: IllegalStateException) {
                // TODO エラー処理
            }
        }
    }
}
