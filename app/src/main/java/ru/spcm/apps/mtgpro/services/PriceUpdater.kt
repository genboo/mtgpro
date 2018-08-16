package ru.spcm.apps.mtgpro.services

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.PriceUpdateDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.dto.PriceHistory
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger
import java.io.IOException

class PriceUpdater(private val appExecutors: AppExecutors,
                   private val priceUpdateDao: PriceUpdateDao,
                   private val scryCardDao: ScryCardDao,
                   private val scryCardApi: ScryCardApi) {

    fun update(): LiveData<UpdateResult> {
        val result = MutableLiveData<UpdateResult>()
        appExecutors.networkIO().execute {
            val watchedCards = priceUpdateDao.getWatchedCardsList()

            var updateCounter = 0
            watchedCards.forEach { item ->
                if (item.card.number != null) {
                    val number = item.card.number?.replace("a", "")?.replace("b", "")
                    var price = getPrice(item.card.set, number ?: "")
                    for (i in 0..9) {
                        if (price != null) {
                            break
                        }
                        Thread.sleep(5000)
                        price = getPrice(item.card.set, number ?: "")
                    }
                    if (price != null) {
                        priceUpdateDao.insert(PriceHistory(item.card.id, price.usd))
                        if (price.eur == null) {
                            price.eur = ""
                        }
                        scryCardDao.insert(price)
                        updateCounter++
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
        return result
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