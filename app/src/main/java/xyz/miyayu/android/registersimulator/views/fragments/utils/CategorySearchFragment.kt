package xyz.miyayu.android.registersimulator.views.fragments.utils

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategorySearchFragmentBinding
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.viewmodel.CategorySearchViewModel
import xyz.miyayu.android.registersimulator.views.adapter.CategorySearchItemAdapter

@AndroidEntryPoint
class CategorySearchFragment : Fragment(R.layout.category_search_fragment) {
    private val viewModel: CategorySearchViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = object : CategorySearchItemAdapter() {
            override fun onItemClicked(item: CategoryAndTaxRate) {
                val categoryId = item.category.categoryId ?: throw NullPointerException("")
                Log.d(this::class.simpleName, categoryId.toString())
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    savedStateHandleKey,
                    categoryId
                )
                findNavController().popBackStack()
            }
        }

        val binding = CategorySearchFragmentBinding.bind(view)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(decoration)

        viewModel.categoryList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        val savedStateHandleKey: String = CategorySearchFragment::class.java.name
    }
}
