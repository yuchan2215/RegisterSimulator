package xyz.miyayu.android.registersimulator.feature.setting.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.setting.R
import xyz.miyayu.android.registersimulator.feature.setting.databinding.CategoryInputFragmentBinding
import xyz.miyayu.android.registersimulator.feature.setting.viewmodel.CategoryInputViewModel
import javax.inject.Inject

@AndroidEntryPoint
internal class CategoryInputFragment : Fragment(R.layout.category_input_fragment) {
    @Inject
    lateinit var viewModelFactory: CategoryInputViewModel.Factory

    private val navigationArgs: CategoryInputFragmentArgs by navArgs()
    private val viewModel: CategoryInputViewModel by viewModels(
        ownerProducer = { this },
        factoryProducer = {
            val args = navigationArgs
            CategoryInputViewModel.provideFactory(viewModelFactory, args)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CategoryInputFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.isInitialized.observe(viewLifecycleOwner) {
            if (it == true) {
                // アニメーションを有効にする
                binding.categoryNameLayout.isHintAnimationEnabled = true

                // 表示アニメーション
                binding.inputFragmentLinearLayout.alpha = 0.0f
                binding.inputFragmentLinearLayout.visibility = View.VISIBLE
                @Suppress("UsePropertyAccessSyntax")
                binding.inputFragmentLinearLayout.animate()
                    .alpha(1.0f)
                    .setDuration(100)

                // オブザーブを解除する
                viewModel.isInitialized.removeObservers(viewLifecycleOwner)
            }
        }

        // セーブボタンの処理
        binding.saveButton.setOnClickListener {
            viewModel.save()
            view.findNavController().popBackStack()
        }
        // キャンセルボタンの処理
        binding.cancelButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}
