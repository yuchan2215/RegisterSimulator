package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategorySettingFragmentBinding
import xyz.miyayu.android.registersimulator.model.entity.Category
import xyz.miyayu.android.registersimulator.viewmodel.CategorySettingViewModel
import xyz.miyayu.android.registersimulator.views.adapter.CategoryListAdapter

class CategorySettingFragment : Fragment(R.layout.category_setting_fragment) {
    private val viewModel: CategorySettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = object : CategoryListAdapter() {
            override fun onItemClicked(category: Category) {
                //TODO クリックされた時の処理
            }
        }

        //RecyclerViewで項目を区切るデコレーション
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        CategorySettingFragmentBinding.bind(view).apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(decoration)
        }

        viewModel.categoryList.observe(this.viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }
}