package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.tools.AppExecutors

class ScryCardBound(appExecutors: AppExecutors,
                    private val cardApi: ScryCardApi) : NetworkBound<ScryCard, ScryCard>(appExecutors) {

    private var set: String = ""
    private var number: String = ""

    override fun saveCallResult(data: ScryCard?) {
        if (data != null) {
            if (cache.size == MAX_CACHE_SIZE) {
                cache.clear()
            }
            cache[set + number] = data
        }
    }

    override fun shouldFetch(data: ScryCard?): Boolean {
        return !cache.containsKey(set + number)
    }

    override fun loadSaved(): LiveData<ScryCard> {
        val data = MutableLiveData<ScryCard>()
        val card = cache[set + number]
        if (card != null) {
            data.setValue(card)
        } else {
            data.setValue(null)
        }
        return data
    }

    override fun createCall(): LiveData<ApiResponse<ScryCard>> {
        return cardApi.getCardByNumber(set.toLowerCase(), number)
    }

    fun setParams(set: String, number: String): ScryCardBound {
        this.set = set
        this.number = number
        return this
    }

    companion object {
        private const val MAX_CACHE_SIZE = 100
        private val cache = HashMap<String, ScryCard>()
    }

}