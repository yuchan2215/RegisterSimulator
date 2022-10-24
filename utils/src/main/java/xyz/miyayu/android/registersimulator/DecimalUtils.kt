package xyz.miyayu.android.registersimulator

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object DecimalUtils {
    private val decimalFormat = DecimalFormat("#,###.##")
    private fun getFormatted(bigDecimal: BigDecimal): String {
        return decimalFormat.format(bigDecimal)
    }

    fun BigDecimal.toFormattedString(): String {
        return getFormatted(this)
    }

    fun BigDecimal.convertToDecimalPoint(): BigDecimal {
        return this.divide(
            100.toBigDecimal(),
            2,
            RoundingMode.HALF_EVEN
        )
    }

    fun BigDecimal?.convertZeroIfNull(): BigDecimal {
        return this ?: "0".toBigDecimal()
    }
}
