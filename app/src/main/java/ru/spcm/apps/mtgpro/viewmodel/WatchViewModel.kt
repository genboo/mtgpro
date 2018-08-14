package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.repository.CollectionRepo
import ru.spcm.apps.mtgpro.model.dto.FilterItem
import ru.spcm.apps.mtgpro.repository.WatchRepo
import java.util.*

import javax.inject.Inject
import kotlin.collections.HashMap


/**
 * Карты для отслеживания
 * Created by gen on 14.08.2018.
 */

class WatchViewModel @Inject
internal constructor(watchRepo: WatchRepo) : ViewModel() {

    val cards: LiveData<PagedList<CardWatched>>  = LivePagedListBuilder(watchRepo.getAllCards(), 20).build()

    init {

    }

}
