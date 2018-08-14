package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.bounds.ScryCardBound
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class CardRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val scryCardApi: ScryCardApi,
            private val cardDao: CardDao,
            private val scryCardDao: ScryCardDao,
            private val cacheDao: CacheDao) {

    fun getPrices(set: String, number: String): LiveData<Resource<ScryCard>> {
        return ScryCardBound(appExecutors, scryCardApi, cacheDao, scryCardDao)
                .setParams(set, number)
                .create()
                .asLiveData()
    }

    fun getCards(id: String): LiveData<List<CardLocal>> {
        return cardDao.getSavedCards(id)
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

    fun updateWatch(id: String, wish: Boolean) {
        appExecutors.diskIO().execute {
            if (wish) {
                cardDao.insert(WatchedCard(id))
            } else {
                cardDao.delete(WatchedCard(id))
            }
        }
    }


}