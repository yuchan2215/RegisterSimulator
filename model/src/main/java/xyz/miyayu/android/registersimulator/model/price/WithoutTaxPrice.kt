package xyz.miyayu.android.registersimulator.model.price

import xyz.miyayu.android.registersimulator.model.R
import xyz.miyayu.android.registersimulator.utils.ResourceService
import java.math.BigDecimal

/**
 * 税抜価格のクラス。
 */
class WithoutTaxPrice(amount: BigDecimal) : Price(amount) {

    constructor(text: String?) : this(convertBigDecimal(text ?: "0"))

    companion object {

        fun WithoutTaxPrice?.getPreview(resourceService: ResourceService): String {
            return resourceService.getResources()
                .getString(R.string.without_tax_preview, this.getFormattedString())
        }

        private fun convertBigDecimal(priceText: String): BigDecimal {
            return priceText.toBigDecimalOrNull() ?: "0".toBigDecimal()
        }

        fun BigDecimal.convertToWithOutTaxPrice(): WithoutTaxPrice {
            return WithoutTaxPrice(this)
        }
    }
}
