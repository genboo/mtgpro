package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.repository.LibrariesRepo

import javax.inject.Inject


/**
 * Список колод
 * Created by gen on 28.06.2018.
 */

class LibrariesViewModel @Inject
internal constructor(private val librariesRepo: LibrariesRepo) : ViewModel() {

    val libraries: LiveData<List<LibraryInfo>> = librariesRepo.getLibraries()

    fun save(library: Library) {
        librariesRepo.save(library)
    }

}
