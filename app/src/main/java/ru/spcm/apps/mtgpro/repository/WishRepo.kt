package ru.spcm.apps.mtgpro.repository

import androidx.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.SetName
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class WishRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao,
            private val setsDao: SetsDao) {

    fun getWishedCards(): LiveData<List<Card>> {
        return cardDao.getWishedCards()
    }

    fun getWishedCardsFiltered(sets: Array<String>): LiveData<List<Card>> {
        return cardDao.getWishedCardsFiltered(sets)
    }

    fun getWishedSetNames():LiveData<List<SetName>>{
        return setsDao.getWishedSetNames()
    }

}