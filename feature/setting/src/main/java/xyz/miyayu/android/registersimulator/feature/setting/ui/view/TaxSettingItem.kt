package xyz.miyayu.android.registersimulator.feature.setting.ui.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import xyz.miyayu.android.registersimulator.feature.setting.R

class TaxSettingItem(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(
            R.layout.tax_setting_item, this, true
        )
    }

    fun setTitle(title: String) {
        this.findViewById<TextInputEditText>(R.id.tax_title_input_edit).setText(title)
    }

    fun setRate(rate: String) {
        this.findViewById<TextInputEditText>(R.id.tax_rate_input_edit).setText(rate)
    }

    fun setLabel(label: String) {
        this.findViewById<TextView>(R.id.rate_label).text = label
    }

    // private var titleTextWatcher: TextWatcher? = null
    fun setTitleTextChangedListener(textWatcher: TextWatcher) {
        // this.titleTextWatcher = textWatcher
        this.findViewById<TextInputEditText>(R.id.tax_title_input_edit)
            .addTextChangedListener(textWatcher)
    }

    fun setRateTextChangedListener(textWatcher: TextWatcher) {
        this.findViewById<TextInputEditText>(R.id.tax_rate_input_edit)
            .addTextChangedListener(textWatcher)
    }
}
