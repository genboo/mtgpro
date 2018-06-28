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
                private val setsDao: SetsDao,
                private val cacheDao: CacheDao,
                private val setsApi: SetsApi) : CachedNetworkBound<List<Set>, List<Set>>(appExecutors) {

    private val type: String
        get() = Set::class.java.simpleName + "::" + METHOD


    override fun saveCallResult(data: List<Set>?) {
        if (data != null) {
            val cacheKey = getCacheKey()
            data.forEach { it ->
                setsDao.insert(it)
            }

            val cache = Cache(cacheKey,
                    Date().time + getCacheTime(cacheDao.getCacheType(type)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Set>?): Boolean {
        return data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Set>> {
        return setsDao.getSets()
    }

    override fun createCall(): LiveData<ApiResponse<List<Set>>> {
        return setsApi.getSets()
    }

    override fun loadCacheTime(): LiveData<Cache> {
        return cacheDao.getCache(getCacheKey())
    }

    private fun getCacheKey(): String {
        return type
    }

    companion object {
        private const val METHOD = "sets"
    }

}