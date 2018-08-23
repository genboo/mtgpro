package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.repository.WatchRepo

import javax.inject.Inject


/**
 * Карты для отслеживания
 * Created by gen on 14.08.2018.
 */

class WatchViewModel @Inject
internal constructor(private val watchRepo: WatchRepo) : ViewModel() {

    val cards: LiveData<PagedList<CardWatched>> = LivePagedListBuilder(watchRepo.getAllCards(), 20).build()

    fun delete(id: String) {
        watchRepo.delete(id)
    }
}
