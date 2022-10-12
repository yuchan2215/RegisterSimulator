package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.ItemListFragmentBinding
import xyz.miyayu.android.registersimulator.model.entity.ProductItemDetail
import xyz.miyayu.android.registersimulator.viewmodel.ItemListViewModel
import xyz.miyayu.android.registersimulator.views.adapter.ItemListAdapter

@AndroidEntryPoint
class ItemListFragment : Fragment(R.layout.item_list_fragment) {
    private val viewModel: ItemListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = object : ItemListAdapter() {
            override fun onItemClicked(item: ProductItemDetail) {
                // TODO
            }
        }

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        ItemListFragmentBinding.bind(view).apply {
            itemRecyclerView.adapter = adapter
            itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            itemRecyclerView.addItemDecoration(decoration)
        }

        viewModel.allItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
