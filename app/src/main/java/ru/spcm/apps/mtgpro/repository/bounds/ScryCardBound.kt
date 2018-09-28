package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class ScryCardBound(appExecutors: AppExecutors,
                    private val scryCardApi: ScryCardApi,
                    private val cacheDao: CacheDao,
                    private val scryCardDao: ScryCardDao) : CachedNetworkBound<ScryCard, ScryCard>(appExecutors) {


    private var set: String = ""
    private var number: String = ""
    private var valute: Float = 1f
    private var force = false

    override fun saveCallResult(data: ScryCard?) {
        if (data != null) {
            if (data.eur == null) {
                data.eur = ""
            }
            if (data.usd == null) {
                data.usd = ""
            }
            scryCardDao.insert(data)
            val cache = Cache(getCacheKey(),
                    Date().time + getCacheTime(cacheDao.getCacheType(TYPE)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: ScryCard?): Boolean {
        return data == null || checkExpireCache(cacheExpire) || force
    }

    override fun loadSaved(): LiveData<ScryCard> {
        return scryCardDao.getPrices(set, number, valute)
    }

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    override fun createCall(): LiveData<ApiResponse<ScryCard>> {
        return scryCardApi.getCardByNumber(set, number)
    }

    private fun getCacheKey(): String {
        return TYPE + set + number
    }

    fun setParams(set: String, number: String, valute: Float): ScryCardBound {
        this.set = set.toLowerCase()
        this.number = number.replace("a", "").replace("b", "")
        this.valute = valute
        return this
    }

    companion object {
        private const val METHOD = "price"
        val TYPE: String = ScryCard::class.java.simpleName + "::" + ScryCardBound.METHOD
        const val EXPIRE: Long = 1000 * 60 * 60 * 24
    }

}