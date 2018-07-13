package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.repository.bounds.SetsBound
import ru.spcm.apps.mtgpro.model.api.SetsApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class SetsRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val setsApi: SetsApi,
            private val setsDao: SetsDao,
            private val cacheDao: CacheDao) {

    fun getSets(force: Boolean): LiveData<Resource<List<Set>>> {
        return SetsBound(appExecutors, setsApi, setsDao, cacheDao)
                .setForce(force)
                .create()
                .asLiveData()
    }

}