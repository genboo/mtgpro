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

    private var searchString: String = ""
    private var language: String? = "russian"
    private var set: String = ""
    private var number: String = ""
    private var name: String = ""

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
                    Date().time + getCacheTime(cacheDao.getCacheType(TYPE)))
            cacheDao.insert(cache)
        }
    }

    override fun shouldFetch(data: List<Card>?): Boolean {
        return data == null || checkExpireCache(cacheExpire)
    }

    override fun loadSaved(): LiveData<List<Card>> {
        return cardDao.getCachedCards(getCacheKey(), 100)
    }

    override fun createCall(): LiveData<ApiResponse<List<Card>>> {
        if (set.isNotEmpty() && number.isNotEmpty()) {
            return cardApi.getCardsByNumber(set, number)
        } else if (set.isNotEmpty() && name.isNotEmpty()) {
            return cardApi.getCardsByName(set, name)
        }
        return cardApi.getCardsByLanguage(searchString, language)
    }

    private fun getCacheKey(): String {
        return TYPE + searchString
    }

    fun setParams(search: String): SearchBound {
        when {
            search.startsWith("e ", true) -> {
                searchString = search.replaceFirst("e ", "", true)
                language = null
            }
            search.startsWith("n ", true) -> {
                searchString = search.replaceFirst("n ", "", true)
                val parts = searchString.split(" ")
                if (parts.size > 1) {
                    set = parts[0]
                    number = parts[1]
                }
            }
            search.startsWith("s ", true) -> {
                searchString = search.replaceFirst("s ", "", true)
                val parts = searchString.split("[ ]".toRegex(), 2)
                if (parts.size > 1) {
                    set = parts[0]
                    name = parts[1]
                }
            }
            else -> searchString = search
        }

        return this
    }

    companion object {
        private const val METHOD = "search"
        val TYPE: String = Card::class.java.simpleName + "::" + SearchBound.METHOD
        const val EXPIRE: Int = 1000 * 60 * 10
    }

}