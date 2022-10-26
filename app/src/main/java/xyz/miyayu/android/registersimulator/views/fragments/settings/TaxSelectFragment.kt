package xyz.miyayu.android.registersimulator.views.fragments.settings

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import xyz.miyayu.android.registersimulator.R
import xyz.miyayu.android.registersimulator.model.TaxRate.Companion.getPreview
import xyz.miyayu.android.registersimulator.utils.ResourceService
import javax.inject.Inject

@AndroidEntryPoint
class TaxSelectFragment : DialogFragment() {

    @Inject
    lateinit var resourceService: ResourceService
    private val navArgs by navArgs<TaxSelectFragmentArgs>()

    private val taxRates by lazy {
        navArgs.taxRates
    }

    private val choiceItems by lazy {
        taxRates.map {
            it.getPreview(resourceService)
        }.toMutableList().apply {
            add(0, getString(R.string.deselection_option))
        }.toTypedArray()
    }

    /**
     * 選択されたアイテムを[savedStateHandleKey]で呼び出し元のFragmentに返す。
     */
    private val positiveListener = DialogInterface.OnClickListener { dialog, _ ->
        val checkedItemPosition = (dialog as AlertDialog).listView.checkedItemPosition
        val selectItem = taxRates.getOrNull(checkedItemPosition - 1) // 選択肢しないという選択肢があるため、１つ引く。
        Log.d(this::class.simpleName, "SelectedItem: $selectItem")
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            savedStateHandleKey,
            selectItem
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val taxRateIndex = taxRates.indexOf(navArgs.selectedTaxRate)
        val checkedNum = if (taxRateIndex == -1) 0 else taxRateIndex + 1 // 選択しないという選択肢があるため、１つ足す。
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_tax_title))
            .setSingleChoiceItems(choiceItems, checkedNum, null)
            .setPositiveButton(getString(R.string.ok_button), positiveListener)
            .setNegativeButton(R.string.cancel_btn_description) { _, _ -> }
        return builder.create()
    }

    companion object {
        val savedStateHandleKey: String = TaxSelectFragment::class.java.name
    }
}
