package xyz.miyayu.android.registersimulator.model.entity.price

import xyz.miyayu.android.registersimulator.DecimalUtils.convertToDecimalPoint
import xyz.miyayu.android.registersimulator.DecimalUtils.convertZeroIfNull
import xyz.miyayu.android.registersimulator.model.entity.TaxRate
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
