package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.CardRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Карточка карты
 * Created by gen on 28.06.2018.
 */

class CardViewModel @Inject
internal constructor(private val cardRepo: CardRepo) : ViewModel() {

    private val switcher: MutableLiveData<String> = MutableLiveData()
    private var card: LiveData<List<CardLocal>>

    init {
        card = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardLocal>>()
            }
            return@switchMap cardRepo.getCards(it)
        }
    }

    fun getCard(): LiveData<List<CardLocal>> {
        return card
    }

    fun loadCard(mid: String) {
        switcher.postValue(mid)
    }

}
