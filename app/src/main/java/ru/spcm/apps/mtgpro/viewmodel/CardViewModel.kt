package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.CardRepo
import ru.spcm.apps.mtgpro.repository.LibrariesRepo
import ru.spcm.apps.mtgpro.repository.PriceRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Карточка карты
 * Created by gen on 28.06.2018.
 */

class CardViewModel @Inject
internal constructor(private val cardRepo: CardRepo,
                     private val librariesRepo: LibrariesRepo,
                     private val priceRepo: PriceRepo) : ViewModel() {

    private val switcher: MutableLiveData<String> = MutableLiveData()
    private val switcherPrice: MutableLiveData<PriceParams> = MutableLiveData()
    private val switcherLibraries: MutableLiveData<Boolean> = MutableLiveData()
    private var cards: LiveData<List<CardLocal>>
    private var libraries: LiveData<List<LibraryInfo>>
    private var librariesByCard: LiveData<List<LibraryInfo>>
    private var wish: LiveData<WishedCard>
    private var prices: LiveData<ScryCard>

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

        libraries = Transformations.switchMap(switcherLibraries) {
            if (it) {
                return@switchMap librariesRepo.getLibraries()
            }
            return@switchMap AbsentLiveData.create<List<LibraryInfo>>()
        }

        librariesByCard = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<LibraryInfo>>()
            }
            return@switchMap librariesRepo.getLibrariesByCard(it)
        }

        prices = Transformations.switchMap(switcherPrice) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<ScryCard>()

            }
            return@switchMap priceRepo.getPricesFromCache(it.set, it.number, it.valute)
        }
    }

    fun getCards(): LiveData<List<CardLocal>> {
        return cards
    }

    fun getLibraries(): LiveData<List<LibraryInfo>> {
        return libraries
    }

    fun getLibrariesByCard(): LiveData<List<LibraryInfo>> {
        return librariesByCard
    }

    fun getWish(): LiveData<WishedCard> {
        return wish
    }

    fun loadCard(id: String) {
        switcher.postValue(id)
    }

    fun loadPrices(set: String, number: String): LiveData<Resource<ScryCard>> {
        return priceRepo.getPrices(set, number)
    }

    fun getPrices(): LiveData<ScryCard> {
        return prices
    }

    fun loadPricesFromCache(set: String, number: String, valute: Float) {
        val params = PriceParams(set, number, valute)
        switcherPrice.postValue(params)
    }

    fun loadLibraries() {
        switcherLibraries.postValue(true)
    }

    fun updateCard(card: Card) {
        cardRepo.updateCard(card)
    }

    fun updateLibraryCard(card: LibraryCard) {
        librariesRepo.updateCard(card)
    }

    fun updateWish(id: String, wish: Boolean) {
        cardRepo.updateWish(id, wish)
    }

    fun updateWatch(id: String, wish: Boolean) {
        cardRepo.updateWatch(id, wish)
    }

    fun addCard(item: LibraryCard) {
        librariesRepo.addCard(item)
    }

    fun findAndUpdateSecondSide(id: String): LiveData<Boolean> {
        return cardRepo.findAndUpdateSecondSide(id)
    }

    data class PriceParams(var set: String, var number: String, var valute: Float)
}
