package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.repository.WatchRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Карты для отслеживания
 * Created by gen on 14.08.2018.
 */

class WatchViewModel @Inject
internal constructor(private val watchRepo: WatchRepo) : ViewModel() {

    private val switch = MutableLiveData<Float>()
    private val cards: LiveData<PagedList<CardWatched>>

    init {
        cards = Transformations.switchMap(switch) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<PagedList<CardWatched>>()
            }
            return@switchMap LivePagedListBuilder(watchRepo.getAllCards(it), 20).build()
        }
    }

    fun getCards(): LiveData<PagedList<CardWatched>> {
        return cards
    }

    fun loadCards(valute: Float) {
        switch.postValue(valute)
    }

    fun delete(id: String) {
        watchRepo.delete(id)
    }
}
