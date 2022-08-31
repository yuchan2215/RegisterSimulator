package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.databinding.CategorySettingFragmentBinding
import xyz.miyayu.android.registersimulator.model.entity.CategoryAndTaxRate
import xyz.miyayu.android.registersimulator.util.ThemeColorUtil
import xyz.miyayu.android.registersimulator.viewmodel.CategorySettingViewModel
import xyz.miyayu.android.registersimulator.views.adapter.CategoryListAdapter

class CategorySettingFragment : Fragment(R.layout.category_setting_fragment) {
    private val viewModel: CategorySettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = object : CategoryListAdapter() {
            override fun onItemClicked(item: CategoryAndTaxRate) {
                val categoryId = item.category.categoryId ?: throw NullPointerException("")
                view.findNavController()
                    .navigate(CategorySettingFragmentDirections.openCategoryInput(categoryId))
            }
        }

        //RecyclerViewで項目を区切るデコレーション
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        CategorySettingFragmentBinding.bind(view).apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(decoration)
            getSwipeToDismissTouchHelper(adapter).attachToRecyclerView(recyclerView)

            addItemButton.setOnClickListener {
                view.findNavController()
                    .navigate(CategorySettingFragmentDirections.openCategoryInput())
            }
        }

        viewModel.categoryList.observe(this.viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun getSwipeToDismissTouchHelper(adapter: CategoryListAdapter) =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                val iconColor = ThemeColorUtil.getThemedColor(
                    requireContext(),
                    com.google.android.material.R.attr.colorOnError
                )

                val backgroundColor = ThemeColorUtil.getThemedColor(
                    requireContext(),
                    com.google.android.material.R.attr.colorError
                )

                val itemView = viewHolder.itemView
                val background = ColorDrawable(backgroundColor)

                val deleteIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.delete)
                    ?: throw NullPointerException()

                deleteIcon.setTint(iconColor)

                val iconMarginVertical = (itemView.height - deleteIcon.intrinsicHeight) / 2

                //もし０なら描画しない
                if (dX == 0f) {
                    return
                }
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt(),
                    itemView.bottom
                )
                deleteIcon.setBounds(
                    itemView.left + iconMarginVertical,
                    itemView.top + iconMarginVertical,
                    itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                    itemView.bottom - iconMarginVertical
                )

                background.draw(c)
                deleteIcon.draw(c)
            }
        })
}