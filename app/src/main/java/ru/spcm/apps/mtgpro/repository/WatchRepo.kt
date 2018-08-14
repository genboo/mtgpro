package ru.spcm.apps.mtgpro.repository

import android.arch.paging.DataSource
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class WatchRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao) {

    fun getAllCards(): DataSource.Factory<Int, CardWatched> {
        return cardDao.getWatchedCards()
    }
}