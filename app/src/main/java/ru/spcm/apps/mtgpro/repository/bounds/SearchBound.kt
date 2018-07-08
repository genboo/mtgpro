package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class SearchBound(appExecutors: AppExecutors,
                  private val cardApi: CardApi,
                  private val cardDao: CardDao,
                  private val additionalInfoCardDao: AdditionalInfoCardDao,
                  private val cacheDao: CacheDao) : CachedNetworkBound<List<Card>, List<Card>>(appExecutors) {

    private val type: String = Card::class.java.simpleName + "::" + SearchBound.METHOD
    private var searchString: String = ""
    private var russian: Boolean = true

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    override fun saveCallResult(data: List<Card>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            data.forEach { card ->
                card.prepare()
                cardDao.insert(card)
                cacheDao.insert(CacheCard(card.id, cacheKey))
                additionalInfoCardDao.updateAdditionInfo(card)
            }
            val cache = Cache(cacheKey,
                    Date().time + getCacheTime(cacheDao.getCacheType(type)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Card>?): Boolean {
        return data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Card>> {
        return cardDao.getCachedCards(getCacheKey())
    }

    override fun createCall(): LiveData<ApiResponse<List<Card>>> {
        return cardApi.getCardsByName(searchString)
    }

    private fun getCacheKey(): String {
        return type + searchString
    }

    fun setParams(searchString: String, russian: Boolean): SearchBound {
        this.searchString = searchString
        this.russian = russian
        return this
    }

    companion object {
        private const val METHOD = "search"
    }

}