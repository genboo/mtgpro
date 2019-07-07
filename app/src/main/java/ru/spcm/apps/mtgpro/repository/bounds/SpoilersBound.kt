package ru.spcm.apps.mtgpro.repository.bounds

import androidx.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class SpoilersBound(appExecutors: AppExecutors,
                    private val cardApi: CardApi,
                    private val cardDao: CardDao,
                    private val additionalInfoCardDao: AdditionalInfoCardDao,
                    private val cacheDao: CacheDao) : CachedNetworkBound<List<Card>, List<Card>>(appExecutors) {


    private var set: String = ""
    private var limit: Int = 0
    private var force: Boolean = false

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    override fun saveCallResult(data: List<Card>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            val saveKey = getSaveKey()
            data.forEach { card ->
                card.prepare()
                cardDao.insert(card)
                cacheDao.insert(CacheCard(card.id, saveKey))
                additionalInfoCardDao.updateAdditionInfo(card)
            }
            val cache = Cache(cacheKey,
                    Date().time + getCacheTime(cacheDao.getCacheType(TYPE)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Card>?): Boolean {
        return data == null || force || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Card>> {
        return cardDao.getCachedCards(getSaveKey(), limit)
    }

    override fun createCall(): LiveData<ApiResponse<List<Card>>> {
        return cardApi.getCardsBySet(set, limit / PAGES_SIZE, PAGES_SIZE)
    }

    private fun getCacheKey(): String {
        return TYPE + set + limit
    }

    private fun getSaveKey(): String {
        return TYPE + set
    }

    fun setParams(set: String, offset: Int, force: Boolean): SpoilersBound {
        this.set = set
        this.limit = offset
        this.force = force
        return this
    }

    companion object {
        private const val METHOD = "spoilers"
        val TYPE: String = Card::class.java.simpleName + "::" + METHOD
        const val EXPIRE: Long = 1000 * 60 * 60 * 24 * 90L
        const val PAGES_SIZE = 42
    }

}