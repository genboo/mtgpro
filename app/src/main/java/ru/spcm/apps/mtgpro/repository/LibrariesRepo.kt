package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.LibrariesDao
import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryCard
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class LibrariesRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val librariesDao: LibrariesDao,
            private val cardDao: CardDao) {

    fun getLibraries(): LiveData<List<LibraryInfo>> {
        return librariesDao.getLibraries()
    }

    fun getCards(library: Long): LiveData<List<CardForLibrary>> {
        return cardDao.getCardsInLibrary(library)
    }

    fun save(item: Library) {
        appExecutors.diskIO().execute { librariesDao.insert(item) }
    }

    fun update(item: Library) {
        appExecutors.diskIO().execute { librariesDao.update(item) }
    }

    fun addCard(item: LibraryCard) {
        appExecutors.diskIO().execute { librariesDao.insert(item) }
    }

}