package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.SetsRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список сетов
 * Created by gen on 22.12.2017.
 */

class SetsViewModel @Inject
internal constructor(private val setsRepo: SetsRepo) : ViewModel() {

    private val switcher: MutableLiveData<Boolean> = MutableLiveData()

    fun getSets(): LiveData<Resource<List<Set>>> {
        return Transformations.switchMap(switcher) {
            if (it) {
                return@switchMap setsRepo.getSets()
            }
            return@switchMap AbsentLiveData.create<Resource<List<Set>>>()
        }
    }

    fun loadSets(){
        switcher.postValue(true)
    }

}
