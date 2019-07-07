package ru.spcm.apps.mtgpro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.PriceUpdateDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.format
import javax.inject.Inject

class CardRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao,
            private val priceUpdateDao: PriceUpdateDao) {

    fun getCards(id: String): LiveData<List<Card>> {
        return cardDao.getSavedCards(id)
    }

    fun getObservedCard(id: String, valute: Float): LiveData<CardObserved> {
        return cardDao.getObservedCard(id, valute)
    }

    fun getWish(id: String): LiveData<WishedCard> {
        return cardDao.getWish(id)
    }

    fun updateCard(card: Card) {
        appExecutors.diskIO().execute {
            if (card.count == 0) {
                cardDao.delete(SavedCard(card.id, 0, card.parent ?: ""))
            } else {
                cardDao.insert(SavedCard(card.id, card.count, card.parent ?: ""))
            }
        }
    }

    fun updateWish(id: String, wish: Boolean) {
        appExecutors.diskIO().execute {
            if (wish) {
                cardDao.insert(WishedCard(id))
            } else {
                cardDao.delete(WishedCard(id))
            }
        }
    }

    fun findAndUpdateSecondSide(id: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        appExecutors.diskIO().execute {
            val card = cardDao.getCardById(id)
            val numberFull = card.number
            var wasUpdate = false
            if (numberFull != null && numberFull.takeLast(1) == "a") {
                val number = numberFull.substring(0, numberFull.length - 1)
                val secondSide = cardDao.getCardBySetAndNumber(card.set, number + "b")
                if (secondSide != null) {
                    cardDao.updateLink(secondSide.id, card.id)
                    wasUpdate = true
                }
            }
            result.postValue(wasUpdate)
        }

        return result
    }

    fun updateWatch(id: String, watch: Boolean) {
        appExecutors.diskIO().execute {
            if (watch) {
                cardDao.insert(WatchedCard(id))
                val price = priceUpdateDao.getPrice(id)
                priceUpdateDao.insert(PriceHistory(id, price.usd.format()))
            } else {
                cardDao.delete(WatchedCard(id))
            }
        }
    }

    fun updateObserve(id: String, observe: Boolean, top: Float, bottom: Float) {
        appExecutors.diskIO().execute {
            val watch = WatchedCard(id)
            watch.observe = observe
            watch.top = top
            watch.bottom = bottom
            cardDao.update(watch)
        }
    }
}