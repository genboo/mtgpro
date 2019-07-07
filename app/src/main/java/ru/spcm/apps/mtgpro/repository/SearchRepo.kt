package ru.spcm.apps.mtgpro.repository

import androidx.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.bounds.SearchBound
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class SearchRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardApi: CardApi,
            private val cardDao: CardDao,
            private val additionalInfoCardDao: AdditionalInfoCardDao,
            private val cacheDao: CacheDao) {

    fun search(searchString: String): LiveData<Resource<List<Card>>> {
        return SearchBound(appExecutors, cardApi, cardDao, additionalInfoCardDao, cacheDao)
                .setParams(searchString)
                .create()
                .asLiveData()
    }

}