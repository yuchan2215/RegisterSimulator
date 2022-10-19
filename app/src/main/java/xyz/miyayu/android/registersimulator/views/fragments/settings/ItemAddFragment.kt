package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
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
import xyz.miyayu.android.registersimulator.databinding.ItemAddFragmentBinding
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
import xyz.miyayu.android.registersimulator.repositories.TaxRateRepository
import xyz.miyayu.android.registersimulator.viewmodel.ItemAddViewModel
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
        binding.selectTaxButton.setOnClickListener {
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
        // 税率選択を受け取る。
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<TaxRate?>(
            TaxSelectFragment.savedStateHandleKey
        )?.observe(viewLifecycleOwner) {
            viewModel.setSelectedTaxRateId(it?.id)
        }
    }
}
