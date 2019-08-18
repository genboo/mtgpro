package ru.spcm.apps.mtgpro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        val result = MutableLiveData<List<LibraryInfo>>()
        appExecutors.diskIO().execute {
            result.postValue(librariesDao.getLibrariesWithColors(valute))
        }
        return result
    }

    fun getLibrary(id: Long): LiveData<Library> {
        return librariesDao.getLibrary(id)
    }

    fun getCards(library: Long, valute: Float): LiveData<List<CardForLibrary>> {
        return cardDao.getCardsInLibrary(library, valute)
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

    fun delete(item: Library) {
        appExecutors.diskIO().execute { librariesDao.delete(item) }
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