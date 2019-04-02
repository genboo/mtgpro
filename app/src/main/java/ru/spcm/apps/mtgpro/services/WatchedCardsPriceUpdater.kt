package ru.spcm.apps.mtgpro.services

import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.PriceUpdateDao
import ru.spcm.apps.mtgpro.model.db.dao.ReportDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.dto.PriceHistory
import ru.spcm.apps.mtgpro.model.dto.Report
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger
import ru.spcm.apps.mtgpro.tools.format
import java.io.IOException
import java.util.*
import javax.inject.Inject

class WatchedCardsPriceUpdater @Inject
constructor(private val appExecutors: AppExecutors,
            private val priceUpdateDao: PriceUpdateDao,
            private val scryCardDao: ScryCardDao,
            private val reportDao: ReportDao,
            private val scryCardApi: ScryCardApi) {

    val result = MutableLiveData<UpdateResult>()

    fun update() {
        appExecutors.networkIO().execute {
            synchronized(appExecutors) {
                priceUpdateDao.deleteBugedPrice()
                val now = Date()
                val watchedCards = priceUpdateDao.getWatchedCardsList()
                reportDao.clear()
                scryCardDao.clearEmpty()

                var updateCounter = 0
                watchedCards.forEach { item ->
                    if (item.card.number != null) {
                        val number = item.card.number?.replace("a", "")?.replace("b", "")
                        var price = getPrice(item.card.set, number ?: "")
                        for (i in 0..9) {
                            if (price != null) {
                                break
                            }
                            Thread.sleep(3000)
                            price = getPrice(item.card.set, number ?: "")
                        }
                        if (price != null) {
                            price.prepare()
                            val lastPrice = priceUpdateDao.getLastPrice(item.card.id, now)
                            priceUpdateDao.insert(PriceHistory(item.card.id, price.usd.format()))
                            scryCardDao.insert(price)

                            if (lastPrice == null || lastPrice.price.isEmpty()) {
                                reportDao.insert(Report(item.card.id, "0.0"))
                            } else {
                                val diff = price.usd.toFloat() - lastPrice.price.toFloat()
                                val diffString = if (diff == 0f) "0.0" else diff.format()
                                reportDao.insert(Report(item.card.id, diffString))
                            }
                            updateCounter++
                        }
                        if (updateCounter % 10 == 0 && updateCounter < watchedCards.size) {
                            appExecutors.mainThread().execute {
                                val data = UpdateResult()
                                data.allCount = watchedCards.size
                                data.currentCard = updateCounter
                                result.postValue(data)
                            }
                        }
                    }
                }
                appExecutors.mainThread().execute {
                    val data = UpdateResult()
                    data.allCount = watchedCards.size
                    data.updatedCount = updateCounter
                    result.postValue(data)
                }
            }
        }
    }

    private fun getPrice(set: String, number: String): ScryCard? {
        var price: ScryCard? = null
        try {
            price = scryCardApi.getCardByNumberCall(set.toLowerCase(), number).execute().body()
        } catch (ex: IOException) {
            Logger.e(ex)
        }
        return price
    }
}