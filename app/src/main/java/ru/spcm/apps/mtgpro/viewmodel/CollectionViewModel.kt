package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.spcm.apps.mtgpro.model.dto.CardCollection
import ru.spcm.apps.mtgpro.repository.CollectionRepo
import ru.spcm.apps.mtgpro.model.dto.FilterItem
import java.util.*

import javax.inject.Inject
import kotlin.collections.HashMap


/**
 * Коллекция карт
 * Created by gen on 03.07.2018.
 */

class CollectionViewModel @Inject
internal constructor(collectionRepo: CollectionRepo) : ViewModel() {

    val cards: LiveData<PagedList<CardCollection>>
    val filters: LiveData<List<FilterItem>> = collectionRepo.getFilters()

    private val switcher = MutableLiveData<CollectionParams>()

    val selectedFilter: List<FilterItem>?
        get() = switcher.value?.filterList

    init {
        cards = Transformations.switchMap(switcher) { params ->
            if (params.filterList == null) {
                return@switchMap LivePagedListBuilder(collectionRepo.getAllCards(params.valute), 20).build()
            }

            val selected = HashMap<String, Array<String>>()
            params.filterList?.forEach { item ->
                val s = item.options.filter { it.selected == true }
                val items = ArrayList<String>()
                if (s.isEmpty()) {
                    item.options.forEach {
                        items.add(it.id)
                    }
                } else {
                    s.forEach {
                        items.add(it.id)
                    }
                }
                selected[item.id] = items.toTypedArray()
            }
            return@switchMap LivePagedListBuilder(collectionRepo.getFilteredCards(params.valute,
                    selected[FilterItem.BLOCK_TYPE] ?: arrayOf(),
                    selected[FilterItem.BLOCK_SUBTYPE] ?: arrayOf(),
                    selected[FilterItem.BLOCK_COLOR] ?: arrayOf(),
                    selected[FilterItem.BLOCK_RARITY] ?: arrayOf(),
                    selected[FilterItem.BLOCK_SET] ?: arrayOf()), 20).build()
        }
    }

    fun loadCards(valute: Float, filter: List<FilterItem>?) {
        switcher.postValue(CollectionParams(valute, filter))
    }

    class CollectionParams(var valute: Float, var filterList: List<FilterItem>?)

}
