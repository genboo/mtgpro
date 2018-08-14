package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.repository.CardRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список сетов
 * Created by gen on 14.08.2018.
 */

class PriceVolatilityViewModel @Inject
internal constructor(private val cardRepo: CardRepo) : ViewModel() {

    private val switcher: MutableLiveData<String> = MutableLiveData()
    private var cards: LiveData<List<CardLocal>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardLocal>>()
            }
            return@switchMap cardRepo.getCards(it)
        }
    }

    fun getCards(): LiveData<List<CardLocal>> {
        return cards
    }

    fun loadCards(id: String) {
        switcher.postValue(id)
    }

}
