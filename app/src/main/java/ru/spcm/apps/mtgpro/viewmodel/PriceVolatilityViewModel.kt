package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.CardObserved
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

    private val switcher: MutableLiveData<CardData> = MutableLiveData()
    private var cards: LiveData<CardObserved>
    private var data: LiveData<List<GraphDot>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<CardObserved>()
            }
            return@switchMap cardRepo.getObservedCard(it.id, it.valute)
        }

        data = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<GraphDot>>()
            }
            val calendar = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -29)
            }
            return@switchMap priceRepo.getData(it.id, it.valute, calendar.time, Date())
        }
    }

    fun getCards(): LiveData<CardObserved> {
        return cards
    }

    fun getData(): LiveData<List<GraphDot>> {
        return data
    }

    fun load(id: String, valute: Float) {
        switcher.postValue(CardData(id, valute))
    }

    fun updateObserve(id: String, observe: Boolean, top: Float, bottom: Float) {
        cardRepo.updateObserve(id, observe, top, bottom)
    }

    class CardData(var id: String, var valute: Float)

}
