package xyz.miyayu.android.registersimulator

import android.content.Context
import android.util.TypedValue

object ThemeColorUtil {

    /**
     * テーマで設定された色を取得します。
     */
    fun getThemedColor(context: Context, resId: Int): Int {
        return TypedValue().apply {
            context.theme.resolveAttribute(resId, this, true)
        }.data
    }
}
