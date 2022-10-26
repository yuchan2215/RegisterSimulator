package xyz.miyayu.android.registersimulator.model.price

import xyz.miyayu.android.registersimulator.model.R
import xyz.miyayu.android.registersimulator.model.TaxRate
import xyz.miyayu.android.registersimulator.utils.DecimalUtils.convertToDecimalPoint
import xyz.miyayu.android.registersimulator.utils.DecimalUtils.convertZeroIfNull
import xyz.miyayu.android.registersimulator.utils.ResourceService
import java.math.BigDecimal

class TaxPrice(
    withoutTaxPrice: WithoutTaxPrice?,
    taxRate: TaxRate?
) : Price(getTaxBigDecimal(withoutTaxPrice, taxRate)) {

    companion object {
        fun TaxPrice?.getTaxPreviewString(resourceService: ResourceService): String {
            return resourceService.getResources()
                .getString(R.string.tax_preview, this.getFormattedString())
        }

        private fun getTaxBigDecimal(
            withoutTaxPrice: WithoutTaxPrice?,
            taxRate: TaxRate?
        ): BigDecimal {
            val price = withoutTaxPrice ?: WithoutTaxPrice("0")
            val rate =
                taxRate?.getBigDecimalPercentRate().convertZeroIfNull().convertToDecimalPoint()
            return price.amount * rate
        }
    }
}
