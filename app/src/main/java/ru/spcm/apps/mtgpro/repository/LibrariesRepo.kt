package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.LibrariesDao
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class LibrariesRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val librariesDao: LibrariesDao) {

    fun getLibraries(): LiveData<List<LibraryInfo>> {
        return librariesDao.getLibraries()
    }

    fun save(item: Library) {
        appExecutors.diskIO().execute { librariesDao.insert(item) }
    }

    fun update(item: Library) {
        appExecutors.diskIO().execute { librariesDao.update(item) }
    }

}