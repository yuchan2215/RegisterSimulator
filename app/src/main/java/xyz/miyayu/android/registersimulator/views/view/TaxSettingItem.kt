package xyz.miyayu.android.registersimulator.views.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.miyayu.android.registersimulator.R

class TaxSettingItem(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(
            R.layout.tax_setting_item, this, true
        )
    }
}