package ru.spcm.apps.mtgpro.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.repository.WishRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData
import ru.spcm.apps.mtgpro.model.dto.SetName
import javax.inject.Inject


/**
 * Список желаний
 * Created by gen on 29.06.2018.
 */

class WishViewModel @Inject
internal constructor(private val wishRepo: WishRepo) : ViewModel() {

    private val filterSwitcher = MutableLiveData<Array<String>>()
    private var cards: LiveData<List<Card>>
    private var sets: LiveData<List<SetName>> = wishRepo.getWishedSetNames()

    val selectedFilter: Array<String>?
        get() = filterSwitcher.value

    init {
        cards = Transformations.switchMap(filterSwitcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<Card>>()
            } else if (it.isEmpty()) {
                return@switchMap wishRepo.getWishedCards()
            }
            return@switchMap wishRepo.getWishedCardsFiltered(it)
        }
    }

    fun getCards(): LiveData<List<Card>> {
        return cards
    }

    fun getWishedSetNames(): LiveData<List<SetName>> {
        return sets
    }

    fun loadWishedCards(data: Array<String>) {
        filterSwitcher.postValue(data)
    }

}
