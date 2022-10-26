package xyz.miyayu.android.registersimulator.price

import java.math.BigDecimal

/**
 * 税抜価格のクラス。
 */
class WithoutTaxPrice(amount: BigDecimal) : Price(amount) {

    constructor(text: String?) : this(convertBigDecimal(text ?: "0"))

    companion object {
        private fun convertBigDecimal(priceText: String): BigDecimal {
            return priceText.toBigDecimalOrNull() ?: "0".toBigDecimal()
        }

        fun BigDecimal.convertToWithOutTaxPrice(): WithoutTaxPrice {
            return WithoutTaxPrice(this)
        }
    }
}
