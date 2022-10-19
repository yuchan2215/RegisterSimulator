package xyz.miyayu.android.registersimulator.util

import java.math.BigDecimal
import java.text.DecimalFormat

object DecimalUtils {
    private val decimalFormat = DecimalFormat("#,###.##")
    fun getSplit(bigDecimal: BigDecimal?): String {
        return decimalFormat.format(bigDecimal ?: "0".toBigDecimal())
    }

    fun BigDecimal?.getSplitString(): String {
        return getSplit(this)
    }
}
