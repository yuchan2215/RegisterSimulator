package xyz.miyayu.android.registersimulator.price

import xyz.miyayu.android.registersimulator.TaxRate
import java.math.BigDecimal

class TaxIncludedPrice(
    private val withoutTaxPrice: WithoutTaxPrice?,
    private val taxRate: TaxRate?
) : Price(getTaxIncludedPrice(withoutTaxPrice, taxRate)) {

    companion object {
        private fun getTaxIncludedPrice(
            withoutTaxPrice: WithoutTaxPrice?,
            taxRate: TaxRate?
        ): BigDecimal {
            val price = withoutTaxPrice ?: WithoutTaxPrice("0")
            val taxPrice = TaxPrice(price, taxRate)
            return price.amount + taxPrice.amount
        }
    }
}
