package ru.spcm.apps.mtgpro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.SetsRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список сетов
 * Created by gen on 28.06.2018.
 */

class SetsViewModel @Inject
internal constructor(private val setsRepo: SetsRepo) : ViewModel() {

    private val switcher: MutableLiveData<Params> = MutableLiveData()
    private var sets: LiveData<Resource<List<Set>>>

    init {
        sets = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Resource<List<Set>>>()
            }
            return@switchMap setsRepo.getSets(it.withArchive, it.force)
        }
    }

    fun getSets(): LiveData<Resource<List<Set>>> {
        return sets
    }

    fun loadSets(withArchive: Boolean, force: Boolean) {
        switcher.postValue(Params(withArchive, force))
    }

    class Params(val withArchive: Boolean, val force: Boolean)
}
