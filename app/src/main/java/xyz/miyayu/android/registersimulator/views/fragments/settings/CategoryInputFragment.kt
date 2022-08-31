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
    private val viewmodel: CategoryInputViewModel by viewModels {
        CategoryInputViewModel.Factory(navigationArgs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CategoryInputFragmentBinding.bind(view)
        //税率を読み込む
        viewmodel.taxRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            val bindTexts = listOf(binding.categoryTax1, binding.categoryTax2, binding.categoryTax3)
            bindTexts.forEachIndexed { index, button ->
                val taxRate = it[index]
                button.text = getString(R.string.category_tax_preview, taxRate.title, taxRate.rate)
            }

            viewmodel.taxRates.removeObservers(viewLifecycleOwner)
            binding.categoryTaxGroup.visibility = View.VISIBLE
        }

        //カテゴリを読み込む
        viewmodel.category.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.categoryNameInput.setText(it.name)
            binding.categoryTaxGroup.check(getTaxRateLayoutId(it.defaultTaxRateId))

            viewmodel.category.removeObservers(viewLifecycleOwner)

            val titleChangedListener = object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    viewmodel.setCategoryName(s.toString())
                }
            }

            val radioChangedListener =
                RadioGroup.OnCheckedChangeListener { _, checkedId ->
                    val taxRateId = getTaxRateId(checkedId, binding)
                    viewmodel.setCategoryTaxRate(taxRateId)
                }

            binding.categoryNameInput.addTextChangedListener(titleChangedListener)
            binding.categoryTaxGroup.setOnCheckedChangeListener(radioChangedListener)
            binding.categoryNameLayout.visibility = View.VISIBLE
        }

        //セーブボタンの処理
        binding.saveButton.setOnClickListener {
            viewmodel.save()
            view.findNavController().popBackStack()
        }
        //キャンセルボタンの処理
        binding.cancelButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun getTaxRateId(taxRateLayoutId: Int?, binding: CategoryInputFragmentBinding): Int {
        return when (taxRateLayoutId) {
            binding.categoryTax1.id -> 1
            binding.categoryTax2.id -> 2
            binding.categoryTax3.id -> 3
            else -> throw IllegalStateException()
        }
    }

    private fun getTaxRateLayoutId(taxRateId: Int?): Int {
        return when (taxRateId) {
            1 -> R.id.category_tax_1
            2 -> R.id.category_tax_2
            3 -> R.id.category_tax_3
            else -> throw IllegalStateException()
        }
    }

}