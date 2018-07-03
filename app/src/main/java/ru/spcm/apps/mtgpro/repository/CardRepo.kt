package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.SavedCard
import ru.spcm.apps.mtgpro.model.dto.WishedCard
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class CardRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardApi: CardApi,
            private val cardDao: CardDao,
            private val cacheDao: CacheDao) {

    fun getCards(id: String): LiveData<List<CardLocal>> {
        return cardDao.getSavedCards(id)
    }

    fun getWish(id: String): LiveData<WishedCard> {
        return cardDao.getWish(id)
    }

    fun updateCard(card: Card) {
        appExecutors.diskIO().execute {
            if (card.count == 0) {
                cardDao.delete(SavedCard(card.id, 0))
            } else {
                cardDao.insert(SavedCard(card.id, card.count))
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

}