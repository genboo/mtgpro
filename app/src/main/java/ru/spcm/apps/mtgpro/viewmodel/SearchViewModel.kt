package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.SearchRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список сетов
 * Created by gen on 28.06.2018.
 */

class SearchViewModel @Inject
internal constructor(private val searchRepo: SearchRepo) : ViewModel() {

    private val switcher: MutableLiveData<String> = MutableLiveData()
    private var cards: LiveData<Resource<List<Card>>>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Resource<List<Card>>>()
            }
            return@switchMap searchRepo.search(it)
        }
    }

    fun getCards(): LiveData<Resource<List<Card>>> {
        return cards
    }

    fun search(searchString: String) {
        switcher.postValue(searchString)
    }

}
