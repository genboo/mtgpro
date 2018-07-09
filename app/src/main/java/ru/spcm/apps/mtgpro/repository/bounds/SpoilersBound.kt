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

class SpoilersBound(appExecutors: AppExecutors,
                    private val cardApi: CardApi,
                    private val cardDao: CardDao,
                    private val additionalInfoCardDao: AdditionalInfoCardDao,
                    private val cacheDao: CacheDao) : CachedNetworkBound<List<Card>, List<Card>>(appExecutors) {

    private val type: String = Card::class.java.simpleName + "::" + SpoilersBound.METHOD
    private var set: String = ""
    private var page: Int = 0

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
                    Date().time + getCacheTime(cacheDao.getCacheType(type)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Card>?): Boolean {
        return data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Card>> {
        return cardDao.getCachedCards(getSaveKey())
    }

    override fun createCall(): LiveData<ApiResponse<List<Card>>> {
        return cardApi.getCardsBySet(set, page, PAGES_SIZE)
    }

    private fun getCacheKey(): String {
        return type + set + page
    }

    private fun getSaveKey(): String {
        return type + set
    }

    fun setParams(set: String, page: Int): SpoilersBound {
        this.set = set
        this.page = page
        return this
    }

    companion object {
        private const val METHOD = "spoilers"
        const val PAGES_SIZE = 24
    }

}