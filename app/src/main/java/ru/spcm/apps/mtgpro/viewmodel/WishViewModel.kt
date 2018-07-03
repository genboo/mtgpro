package ru.spcm.apps.mtgpro.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.repository.WishRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список желаний
 * Created by gen on 29.06.2018.
 */

class WishViewModel @Inject
internal constructor(private val wishRepo: WishRepo) : ViewModel() {

    private val switcher: MutableLiveData<Boolean> = MutableLiveData()
    private var cards: LiveData<List<Card>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it) {
                return@switchMap wishRepo.getWishedCards()
            }
            return@switchMap AbsentLiveData.create<List<Card>>()
        }
    }

    fun getCards(): LiveData<List<Card>> {
        return cards
    }

    fun loadWishedCards() {
        switcher.postValue(true)
    }

}
