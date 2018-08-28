package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.repository.LibrariesRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData

import javax.inject.Inject


/**
 * Список колод
 * Created by gen on 28.06.2018.
 */

class LibrariesViewModel @Inject
internal constructor(private val librariesRepo: LibrariesRepo) : ViewModel() {

    private val libraries: LiveData<List<LibraryInfo>>
    private val switcher = MutableLiveData<Float>()

    init {
        libraries = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<LibraryInfo>>()
            }
            return@switchMap librariesRepo.getLibraries(it)
        }
    }

    fun loadLibraries(valute: Float) {
        switcher.postValue(valute)
    }

    fun getLibraries(): LiveData<List<LibraryInfo>> {
        return libraries
    }

    fun save(library: Library) {
        librariesRepo.save(library)
    }


}
