package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class CardBound(appExecutors: AppExecutors,
                private val cardApi: CardApi,
                private val cardDao: CardDao,
                private val cacheDao: CacheDao) : CachedNetworkBound<List<Card>, List<Card>>(appExecutors) {

    private val type: String = Set::class.java.simpleName + "::" + METHOD
    private var mid: String = ""

    override fun saveCallResult(data: List<Card>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            val cache = Cache(cacheKey,
                    Date().time + getCacheTime(cacheDao.getCacheType(type)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Card>?): Boolean {
        return data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Card>> {
        return cardDao.getSavedCards(mid)
    }

    override fun createCall(): LiveData<ApiResponse<List<Card>>> {
        return cardApi.getCardsByMid(mid)
    }

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    private fun getCacheKey(): String {
        return type
    }

    fun setParams(mid: String): CardBound {
        this.mid = mid
        return this
    }

    companion object {
        private const val METHOD = "sets"
    }

}