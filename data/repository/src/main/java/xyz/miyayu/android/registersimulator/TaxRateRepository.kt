package xyz.miyayu.android.registersimulator

import xyz.miyayu.android.registersimulator.dao.TaxRateDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaxRateRepository @Inject constructor(
    private val taxRateDao: TaxRateDao
) {

    private val defaultTaxRates = listOf(
        TaxRate(id = 1, title = "標準税率", rate = "10"),
        TaxRate(id = 2, title = "軽減税率", rate = "8"),
        TaxRate(id = 3, title = "予備税率", rate = "20")
    )

    /**
     * 税率一覧を取得します。
     * もしデータベース上に存在しない場合は、デフォルトの設定を利用します。
     */
    suspend fun getTaxRates(): List<TaxRate> {
        return defaultTaxRates.map {
            return@map taxRateDao.getTaxRate(it.id) ?: it
        }
    }

    suspend fun getTaxRate(taxRateId: Int): TaxRate? {
        return getTaxRates().singleOrNull {
            it.id == taxRateId
        }
    }

    suspend fun saveTaxRates(taxRates: List<TaxRate>) {
        taxRates.forEach {
            taxRateDao.insert(it)
        }
    }

    suspend fun init() {
        saveTaxRates(getTaxRates())
    }
}
