package ru.spcm.apps.mtgpro.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.SpoilersRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Спойлеры по сету
 * Created by gen on 29.06.2018.
 */

class SpoilersViewModel @Inject
internal constructor(private val spoilersRepo: SpoilersRepo) : ViewModel() {

    private val switcher: MutableLiveData<Params> = MutableLiveData()
    private var cards: LiveData<Resource<List<Card>>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Resource<List<Card>>>()
            }
            return@switchMap spoilersRepo.getSpoilers(it.set, it.limit)
        }
    }

    fun getSpoilers(): LiveData<Resource<List<Card>>> {
        return cards
    }

    fun loadSpoilers(set: String, limit: Int) {
        switcher.postValue(Params(set, limit))
    }

    private inner class Params(val set: String, val limit: Int)

}
