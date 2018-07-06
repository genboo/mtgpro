package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.repository.LibrariesRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Колода
 * Created by gen on 28.06.2018.
 */

class LibraryViewModel @Inject
internal constructor(private val librariesRepo: LibrariesRepo) : ViewModel() {

    private val switcher = MutableLiveData<Long>()

    var cards: LiveData<List<CardForLibrary>>
    var mana: LiveData<List<LibraryManaState>>
    var colors: LiveData<List<LibraryColorState>>
    var library: LiveData<Library>

    val data = MutableLiveData<LibraryData>()

    init {
        data.postValue(LibraryData())

        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardForLibrary>>()
            }
            return@switchMap librariesRepo.getCards(it)
        }

        mana = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<LibraryManaState>>()
            }
            return@switchMap librariesRepo.getLibraryManaState(it)
        }

        colors = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<LibraryColorState>>()
            }
            return@switchMap librariesRepo.getLibraryColorState(it)
        }

        library = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Library>()
            }
            return@switchMap librariesRepo.getLibrary(it)
        }

    }

    fun loadCards(library: Long) {
        switcher.postValue(library)
    }

    fun setCards(cards: List<CardForLibrary>) {
        data.value?.cards = cards
        data.postValue(data.value)
    }

    fun setManaState(states: List<LibraryManaState>) {
        data.value?.manaState = states
        data.postValue(data.value)
    }

    fun setColorState(states: List<LibraryColorState>) {
        data.value?.colorState = states
        data.postValue(data.value)
    }

}
