package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.repository.CollectionRepo

import javax.inject.Inject


/**
 * Коллекция карт
 * Created by gen on 03.07.2018.
 */

class CollectionViewModel @Inject
internal constructor(collectionRepo: CollectionRepo) : ViewModel() {

    val allCards: LiveData<PagedList<Card>> = LivePagedListBuilder(
            collectionRepo.getAllCards(), 20
    ).build()

}
