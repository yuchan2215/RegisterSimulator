package xyz.miyayu.android.registersimulator.feature.setting.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.feature.setting.R
import xyz.miyayu.android.registersimulator.feature.setting.databinding.ItemListFragmentBinding
import xyz.miyayu.android.registersimulator.feature.setting.ui.adapter.ItemListAdapter
import xyz.miyayu.android.registersimulator.feature.setting.viewmodel.ItemListViewModel
import xyz.miyayu.android.registersimulator.model.ProductItemDetail
import xyz.miyayu.android.registersimulator.utils.ResourceService
import javax.inject.Inject

@AndroidEntryPoint
class ItemListFragment : Fragment(R.layout.item_list_fragment) {
    @Inject
    lateinit var resourceService: ResourceService

    private val viewModel: ItemListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**既存のアイテムがタップされたら修正する*/
        val adapter = object : ItemListAdapter(resourceService) {
            override fun onItemClicked(item: ProductItemDetail) {
                findNavController().navigate(
                    ItemListFragmentDirections.openItemAdd(item.item.id ?: -1)
                )
            }
        }

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        ItemListFragmentBinding.bind(view).apply {
            itemRecyclerView.adapter = adapter
            itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            itemRecyclerView.addItemDecoration(decoration)

            addItemButton.setOnClickListener {
                view.findNavController().navigate(
                    ItemListFragmentDirections.openItemAdd()
                )
            }
        }

        viewModel.allItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
