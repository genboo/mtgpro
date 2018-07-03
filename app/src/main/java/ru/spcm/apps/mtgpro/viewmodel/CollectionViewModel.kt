package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.repository.CollectionRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Коллекция карт
 * Created by gen on 03.07.2018.
 */

class CollectionViewModel @Inject
internal constructor(private val collectionRepo: CollectionRepo) : ViewModel() {

    private val switcher: MutableLiveData<Int> = MutableLiveData()
    private var cards: LiveData<List<Card>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<Card>>()
            }
            return@switchMap collectionRepo.getAllCards(it)
        }
    }

    fun getCards(): LiveData<List<Card>> {
        return cards
    }

    fun loadCards(offset: Int) {
        switcher.postValue(offset)
    }

}
