package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategoryInputFragmentBinding
import xyz.miyayu.android.registersimulator.viewmodel.CategoryInputViewModel

class CategoryInputFragment : Fragment(R.layout.category_input_fragment) {
    private val navigationArgs: CategoryInputFragmentArgs by navArgs()
    private val viewModel: CategoryInputViewModel by viewModels {
        CategoryInputViewModel.Factory(navigationArgs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CategoryInputFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //セーブボタンの処理
        binding.saveButton.setOnClickListener {
            viewModel.save()
            view.findNavController().popBackStack()
        }
        //キャンセルボタンの処理
        binding.cancelButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

}