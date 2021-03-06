package ru.spcm.apps.mtgpro.repository.bounds

import androidx.lifecycle.LiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.CacheTime
import ru.spcm.apps.mtgpro.tools.AppExecutors
import java.util.Date

abstract class CachedNetworkBound<R, Q>
@MainThread
constructor(appExecutors: AppExecutors) : NetworkBound<R, Q>(appExecutors) {

    internal var cacheExpire: Long = 0

    override fun create() : NetworkBound<R, Q> {
        val cacheTime = loadCacheTime()
        result.addSource(cacheTime) { cache ->
            result.removeSource(cacheTime)
            if (cache != null) {
                cacheExpire = cache.expire
            }
            super.create()
        }
        return this
    }

    @WorkerThread
    fun getCacheTime(cacheTime: CacheTime?): Long {
        return cacheTime?.time ?: DEFAULT_CACHE_TIME
    }

    fun checkExpireCache(expire: Long): Boolean {
        return expire < Date().time
    }

    @MainThread
    protected abstract fun loadCacheTime(): LiveData<Cache>

    companion object {
        private const val DEFAULT_CACHE_TIME = 15 * 24 * 60 * 60 * 1000L
    }

}