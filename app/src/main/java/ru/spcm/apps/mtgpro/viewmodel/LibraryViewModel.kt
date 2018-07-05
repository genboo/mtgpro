package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
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

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardForLibrary>>()
            }
            return@switchMap librariesRepo.getCards(it)
        }
    }

    fun loadCards(library: Long) {
        switcher.postValue(library)
    }

}
