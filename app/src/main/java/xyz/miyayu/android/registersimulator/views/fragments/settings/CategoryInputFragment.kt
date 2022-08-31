package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategoryInputFragmentBinding
import xyz.miyayu.android.registersimulator.util.SimpleTextWatcher
import xyz.miyayu.android.registersimulator.viewmodel.CategoryInputViewModel

class CategoryInputFragment : Fragment(R.layout.category_input_fragment) {
    private val navigationArgs: CategoryInputFragmentArgs by navArgs()
    private val viewModel: CategoryInputViewModel by viewModels {
        CategoryInputViewModel.Factory(navigationArgs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CategoryInputFragmentBinding.bind(view)

        /**
         * カテゴリのタイトルが変わった時のリスナー
         */
        val titleChangedListener = object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setCategoryName(s.toString())
            }
        }

        /**
         * ラジオボタンの値が変わった時のリスナー
         */
        val radioChangedListener =
            RadioGroup.OnCheckedChangeListener { _, checkedId ->
                val taxRateId = getTaxRateIdFromLayoutId(checkedId, binding)
                viewModel.setCategoryTaxRate(taxRateId)
            }

        //税率を読み込む
        viewModel.taxRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            //税率のテキストを表示する
            val bindTexts = listOf(binding.categoryTax1, binding.categoryTax2, binding.categoryTax3)
            bindTexts.forEachIndexed { index, button ->
                val taxRate = it[index]
                button.text = getString(R.string.category_tax_preview, taxRate.title, taxRate.rate)
            }

            //オブザーバーを削除する
            viewModel.taxRates.removeObservers(viewLifecycleOwner)

            //表示する
            binding.categoryTaxGroup.visibility = View.VISIBLE
        }

        //カテゴリを読み込む
        viewModel.category.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            // チェックした時のアニメーションを無効化する
            val animationClearListener = RadioGroup.OnCheckedChangeListener { _, _ ->
                binding.categoryTaxGroup.jumpDrawablesToCurrentState()
            }
            binding.categoryTaxGroup.setOnCheckedChangeListener(animationClearListener)

            //初期値を入れる
            binding.categoryNameInput.setText(it.name)
            binding.categoryTaxGroup.check(getTaxRateLayoutIdFromTaxRateId(it.defaultTaxRateId))

            //アニメーションを再度有効にする
            binding.categoryNameLayout.isHintAnimationEnabled = true

            //リスナーを設定する
            binding.categoryNameInput.addTextChangedListener(titleChangedListener)
            binding.categoryTaxGroup.setOnCheckedChangeListener(radioChangedListener)

            //オブザーバーを削除する
            viewModel.category.removeObservers(viewLifecycleOwner)

            //表示する
            binding.categoryNameLayout.visibility = View.VISIBLE
        }

        //セーブできるかどうかを反映させる
        viewModel.canSave.observe(viewLifecycleOwner) {
            binding.saveButton.isEnabled = it
        }

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

    private fun getTaxRateIdFromLayoutId(
        taxRateLayoutId: Int?,
        binding: CategoryInputFragmentBinding
    ): Int {
        return when (taxRateLayoutId) {
            binding.categoryTax1.id -> 1
            binding.categoryTax2.id -> 2
            binding.categoryTax3.id -> 3
            else -> throw IllegalStateException()
        }
    }

    private fun getTaxRateLayoutIdFromTaxRateId(taxRateId: Int?): Int {
        return when (taxRateId) {
            1 -> R.id.category_tax_1
            2 -> R.id.category_tax_2
            3 -> R.id.category_tax_3
            else -> throw IllegalStateException()
        }
    }

}