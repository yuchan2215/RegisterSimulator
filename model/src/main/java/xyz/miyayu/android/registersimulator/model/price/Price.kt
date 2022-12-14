package xyz.miyayu.android.registersimulator.model.price

import xyz.miyayu.android.registersimulator.utils.DecimalUtils.convertZeroIfNull
import xyz.miyayu.android.registersimulator.utils.DecimalUtils.toFormattedString
import java.math.BigDecimal

abstract class Price(
    val amount: BigDecimal
) {
    override fun toString(): String {
        return amount.toString()
    }

    companion object {
        fun Price?.getFormattedString(): String {
            val amount = this?.amount.convertZeroIfNull()
            return amount.toFormattedString()
        }
    }
}
