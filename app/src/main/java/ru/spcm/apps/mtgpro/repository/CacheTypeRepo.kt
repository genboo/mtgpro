package ru.spcm.apps.mtgpro.repository

import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.dto.CacheTime
import ru.spcm.apps.mtgpro.repository.bounds.ScryCardBound
import ru.spcm.apps.mtgpro.repository.bounds.SearchBound
import ru.spcm.apps.mtgpro.repository.bounds.SetsBound
import ru.spcm.apps.mtgpro.repository.bounds.SpoilersBound
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class CacheTypeRepo @Inject
constructor(private val appExecutors: AppExecutors, private val cacheDao: CacheDao) {

    fun updateCacheType() {
        appExecutors.diskIO().execute {
            cacheDao.insert(CacheTime(SetsBound.TYPE, SetsBound.EXPIRE))
            cacheDao.insert(CacheTime(SpoilersBound.TYPE, SpoilersBound.EXPIRE))
            cacheDao.insert(CacheTime(SearchBound.TYPE, SearchBound.EXPIRE))
            cacheDao.insert(CacheTime(ScryCardBound.TYPE, ScryCardBound.EXPIRE))
        }
    }

}