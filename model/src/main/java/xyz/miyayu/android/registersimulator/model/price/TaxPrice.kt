package xyz.miyayu.android.registersimulator.model.price

import xyz.miyayu.android.registersimulator.model.TaxRate
import xyz.miyayu.android.registersimulator.utils.DecimalUtils.convertToDecimalPoint
import xyz.miyayu.android.registersimulator.utils.DecimalUtils.convertZeroIfNull
import java.math.BigDecimal

class TaxPrice(
    private val withoutTaxPrice: WithoutTaxPrice?,
    private val taxRate: TaxRate?
) : Price(getTaxBigDecimal(withoutTaxPrice, taxRate)) {

    companion object {
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
