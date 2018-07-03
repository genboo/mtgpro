package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class CollectionRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao) {

    fun getAllCards(offset: Int): LiveData<List<Card>> {
        return cardDao.getAllCards(offset, PAGES_SIZE)
    }

    fun getAllCards(): DataSource.Factory<Int, Card> {
        return cardDao.getAllCards()
    }

    companion object {
        const val PAGES_SIZE = 20
    }

}