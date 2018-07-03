package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class CollectionRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao) {

    fun getAllCards(offset: Int): LiveData<List<Card>> {
        return cardDao.getAllCards(offset)
    }

    companion object {
        const val PAGES_SIZE = 20
    }

}