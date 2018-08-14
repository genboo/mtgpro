package ru.spcm.apps.mtgpro.services

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

    fun update() {
        appExecutors.networkIO().execute {
            val watchedCards = priceUpdateDao.getWatchedCardsList()

            watchedCards.forEach { item ->
                if (item.card.number != null) {
                    var price = getPrice(item.card.set, item.card.number ?: "")
                    for (i in 0..9) {
                        if (price != null) {
                            break
                        }
                        price = getPrice(item.card.set, item.card.number ?: "")
                    }
                    if(price != null) {
                        priceUpdateDao.insert(PriceHistory(item.card.id, price.usd))
                        scryCardDao.insert(price)
                    }
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