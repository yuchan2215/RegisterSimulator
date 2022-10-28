package xyz.miyayu.android.registersimulator.model.price

import xyz.miyayu.android.registersimulator.model.R
import xyz.miyayu.android.registersimulator.model.TaxRate
import xyz.miyayu.android.registersimulator.utils.ResourceService
import java.math.BigDecimal

class TaxIncludedPrice(
    withoutTaxPrice: WithoutTaxPrice?,
    taxRate: TaxRate?
) : Price(getTaxIncludedPrice(withoutTaxPrice, taxRate)) {

    companion object {
        fun TaxIncludedPrice?.getPreview(resourceService: ResourceService): String {
            return resourceService.getResources()
                .getString(R.string.tax_included_preview, this.getFormattedString())
        }

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
