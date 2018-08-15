package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.dto.GraphDot
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.bounds.ScryCardBound
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*
import javax.inject.Inject

class PriceRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val scryCardApi: ScryCardApi,
            private val scryCardDao: ScryCardDao,
            private val cacheDao: CacheDao) {

    fun getPrices(set: String, number: String): LiveData<Resource<ScryCard>> {
        return ScryCardBound(appExecutors, scryCardApi, cacheDao, scryCardDao)
                .setParams(set, number)
                .create()
                .asLiveData()
    }

    fun getData(id: String, from: Date, to: Date): LiveData<List<GraphDot>> {
        return scryCardDao.getData(id, from, to)
    }

}