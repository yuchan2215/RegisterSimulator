package xyz.miyayu.android.registersimulator.feature.common.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.common.R
import xyz.miyayu.android.registersimulator.feature.common.databinding.CategorySearchFragmentBinding
import xyz.miyayu.android.registersimulator.feature.common.ui.adapter.CategorySearchItemAdapter
import xyz.miyayu.android.registersimulator.feature.common.viewmodel.CategorySearchViewModel
import xyz.miyayu.android.registersimulator.model.CategoryDetail
import xyz.miyayu.android.registersimulator.utils.ResourceService
import javax.inject.Inject

/**
 * カテゴリを選択するためのフラグメント。
 */
@AndroidEntryPoint
class CategorySelectFragment : Fragment(R.layout.category_search_fragment) {
    @Inject
    lateinit var resourceService: ResourceService

    private val viewModel: CategorySearchViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        /**選択されたらナビゲーション経由で選択されたものを返す。*/
        val adapter = object : CategorySearchItemAdapter(resourceService) {
            override fun onItemClicked(item: CategoryDetail) {
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
        val savedStateHandleKey: String = CategorySelectFragment::class.java.name
    }
}
