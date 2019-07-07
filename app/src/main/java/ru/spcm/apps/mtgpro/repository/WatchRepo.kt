package ru.spcm.apps.mtgpro.repository

import androidx.paging.DataSource
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.model.dto.WatchedCard
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class WatchRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao) {

    fun getAllCards(valute: Float): DataSource.Factory<Int, CardWatched> {
        return cardDao.getWatchedCards(valute)
    }

    fun delete(id: String) {
        appExecutors.diskIO().execute{
            cardDao.delete(WatchedCard(id))
        }
    }

}