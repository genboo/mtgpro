package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.CacheCard
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class SpoilersBound(appExecutors: AppExecutors,
                    private val cardApi: CardApi,
                    private val cardDao: CardDao,
                    private val cacheDao: CacheDao) : CachedNetworkBound<List<Card>, List<Card>>(appExecutors) {

    private val type: String = Set::class.java.simpleName + "::" + SpoilersBound.METHOD
    private var set: String = ""
    private var page: Int = 0

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    override fun saveCallResult(data: List<Card>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            data.forEach { it ->
                it.prepare()
                cardDao.insert(it)
                cacheDao.insert(CacheCard(it.id, cacheKey))
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
        return cardApi.getCardsBySet(set, page, PAGES_SIZE)
    }

    private fun getCacheKey(): String {
        return type + set + page
    }

    fun setParams(set: String, page: Int): SpoilersBound {
        this.set = set
        this.page = page
        return this
    }

    companion object {
        private const val METHOD = "spoilers"
        const val PAGES_SIZE = 39
    }

}