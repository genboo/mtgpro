package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.LibrariesDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class LibrariesRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val librariesDao: LibrariesDao,
            private val cardDao: CardDao) {

    fun getLibraries(valute: Float): LiveData<List<LibraryInfo>> {
        return librariesDao.getLibraries(valute)
    }

    fun getLibrary(id: Long): LiveData<Library> {
        return librariesDao.getLibrary(id)
    }

    fun getCards(library: Long): LiveData<List<CardForLibrary>> {
        return cardDao.getCardsInLibrary(library)
    }

    fun getLibraryManaState(library: Long): LiveData<List<LibraryManaState>> {
        return librariesDao.getLibraryManaState(library)
    }

    fun getLibraryColorState(library: Long): LiveData<List<LibraryColorState>> {
        return librariesDao.getLibraryColorState(library)
    }

    fun getLibrariesByCard(id: String): LiveData<List<LibraryInfo>> {
        return librariesDao.getLibrariesByCard(id)
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

    fun updateCard(card: LibraryCard) {
        appExecutors.diskIO().execute {
            if (card.count == 0) {
                librariesDao.delete(card.libraryId, card.cardId)
            } else {
                librariesDao.insert(card)
            }
        }
    }

}