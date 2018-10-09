package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.api.SetsApi
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.*

class SetsBound(appExecutors: AppExecutors,
                private val setsApi: SetsApi,
                private val setsDao: SetsDao,
                private val cacheDao: CacheDao) : CachedNetworkBound<List<Set>, List<Set>>(appExecutors) {


    private var force = false
    private var withArchive = false

    override fun saveCallResult(data: List<Set>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            setsDao.insert(data)
            val cache = Cache(cacheKey,
                    Date().time + getCacheTime(cacheDao.getCacheType(TYPE)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Set>?): Boolean {
        return force || data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Set>> {
        if (withArchive) {
            return setsDao.getSetsWithArchive()
        }
        return setsDao.getSets()
    }

    override fun createCall(): LiveData<ApiResponse<List<Set>>> {
        return setsApi.getSets()
    }

    fun setForce(flag: Boolean): SetsBound {
        force = flag
        return this
    }

    fun setWithArchive(flag: Boolean): SetsBound {
        withArchive = flag
        return this
    }

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    private fun getCacheKey(): String {
        return TYPE
    }

    companion object {
        private const val METHOD = "sets"
        const val EXPIRE: Long = 1000 * 60 * 60 * 24 * 30L
        val TYPE: String = Set::class.java.simpleName + "::" + METHOD
    }

}