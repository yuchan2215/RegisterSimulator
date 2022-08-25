package xyz.miyayu.android.registersimulator.repositories

import xyz.miyayu.android.registersimulator.RegisterApplication
import xyz.miyayu.android.registersimulator.model.entity.TaxRate

object TaxRateRepository {
    private val default_tax_rates = listOf(
        TaxRate(id = 0, title = "標準税率", rate = "10"),
        TaxRate(id = 1, title = "軽減税率", rate = "8"),
        TaxRate(id = 2, title = "予備税率", rate = "20")
    )

    private fun getTaxRateDao() = RegisterApplication.instance.database.taxRateDao()

    /**
     * 税率一覧を取得します。
     * もしデータベース上に存在しない場合は、デフォルトの設定を利用します。
     */
    suspend fun getTaxRates(): List<TaxRate> {
        val dao = getTaxRateDao()
        return default_tax_rates.map {
            return@map dao.getTaxRate(it.id) ?: it
        }
    }

    suspend fun saveTaxRates(taxRates: List<TaxRate>) {
        val dao = getTaxRateDao()
        taxRates.forEach {
            dao.insert(it)
        }
    }
}