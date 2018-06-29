package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.bounds.SpoilersBound
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class SpoilersRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardApi: CardApi,
            private val cardDao: CardDao,
            private val cacheDao: CacheDao) {

    fun getSpoilers(set: String, page: Int): LiveData<Resource<List<Card>>> {
        return SpoilersBound(appExecutors, cardApi, cardDao, cacheDao)
                .setParams(set, page)
                .create()
                .asLiveData()
    }

}