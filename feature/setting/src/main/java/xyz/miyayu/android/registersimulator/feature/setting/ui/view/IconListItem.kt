package xyz.miyayu.android.registersimulator.feature.setting.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.miyayu.android.registersimulator.feature.setting.R

internal class IconListItem(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(
            R.layout.setting_fragment_list_item, this, true
        ) as IconListItem
        context.theme.obtainStyledAttributes(attrs, R.styleable.IconListItem, 0, 0)
            .apply {
                val bodyText = getString(R.styleable.IconListItem_bodyText)
                val startIcon = getResourceId(R.styleable.IconListItem_startIcon, 0)

                setText(bodyText)
                setResource(startIcon)
                this@IconListItem.contentDescription = bodyText
            }
    }

    private val textView: TextView get() = this.findViewById(R.id.item_text_view)
    private fun setText(text: String?) {
        textView.text = text
    }

    private val imageView: ImageView get() = this.findViewById(R.id.list_item_start_image)
    private fun setResource(@DrawableRes id: Int) {
        imageView.setImageResource(id)
    }
}
