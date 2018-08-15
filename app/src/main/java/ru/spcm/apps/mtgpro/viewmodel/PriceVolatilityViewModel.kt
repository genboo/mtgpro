package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.GraphDot
import ru.spcm.apps.mtgpro.repository.CardRepo
import ru.spcm.apps.mtgpro.repository.PriceRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData
import java.util.*

import javax.inject.Inject


/**
 * Список сетов
 * Created by gen on 14.08.2018.
 */

class PriceVolatilityViewModel @Inject
internal constructor(private val cardRepo: CardRepo,
                     private val priceRepo: PriceRepo) : ViewModel() {

    private val switcher: MutableLiveData<String> = MutableLiveData()
    private var cards: LiveData<List<CardLocal>>
    private var data: LiveData<List<GraphDot>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardLocal>>()
            }
            return@switchMap cardRepo.getCards(it)
        }

        data = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<GraphDot>>()
            }
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, -30)
            }
            return@switchMap priceRepo.getData(it, calendar.time, Date())
        }
    }

    fun getCards(): LiveData<List<CardLocal>> {
        return cards
    }

    fun getData(): LiveData<List<GraphDot>> {
        return data
    }

    fun load(id: String) {
        switcher.postValue(id)
    }

}
