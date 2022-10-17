package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.ItemAddFragmentBinding
import xyz.miyayu.android.registersimulator.viewmodel.ItemAddViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ItemAddFragment : Fragment(R.layout.item_add_fragment) {
    @Inject
    lateinit var viewModelFactory: ItemAddViewModel.Factory

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
    }
}
