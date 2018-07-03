package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.WishedCard
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
    private var cards: LiveData<List<CardLocal>>
    private var wish: LiveData<WishedCard>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardLocal>>()
            }
            return@switchMap cardRepo.getCards(it)
        }

        wish = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<WishedCard>()
            }
            return@switchMap cardRepo.getWish(it)
        }
    }

    fun getCards(): LiveData<List<CardLocal>> {
        return cards
    }

    fun getWish(): LiveData<WishedCard> {
        return wish
    }

    fun loadCard(id: String) {
        switcher.postValue(id)
    }

    fun updateCard(card: Card) {
        cardRepo.updateCard(card)
    }

    fun updateWish(id:String, wish: Boolean) {
        cardRepo.updateWish(id, wish)
    }
}
