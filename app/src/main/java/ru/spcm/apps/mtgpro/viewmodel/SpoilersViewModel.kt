package ru.spcm.apps.mtgpro.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.repository.SetsRepo
import ru.spcm.apps.mtgpro.repository.SettingsRepo
import ru.spcm.apps.mtgpro.repository.SpoilersRepo
import ru.spcm.apps.mtgpro.tools.AbsentLiveData
import ru.spcm.apps.mtgpro.model.dto.Set

import javax.inject.Inject


/**
 * Спойлеры по сету
 * Created by gen on 29.06.2018.
 */

class SpoilersViewModel @Inject
internal constructor(private val spoilersRepo: SpoilersRepo,
                     private val settingsRepo: SettingsRepo,
                     private val setsRepo: SetsRepo) : ViewModel() {

    private val switcher: MutableLiveData<Params> = MutableLiveData()
    var cards: LiveData<Resource<List<Card>>>
    var set: LiveData<Set>

    init {
        cards = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Resource<List<Card>>>()
            }
            return@switchMap spoilersRepo.getSpoilers(it.set, it.limit, it.force)
        }

        set = Transformations.switchMap(switcher) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<Set>()
            }
            return@switchMap setsRepo.getSet(it.set)
        }
    }

    fun loadSpoilers(set: String, limit: Int, force: Boolean = false) {
        switcher.postValue(Params(set, limit, force))
    }

    fun updateSetting(type: Setting.Type, value: Int) {
        settingsRepo.updateSetting(type, value.toString())
    }

    fun toggleArchive(set: Set) {
        setsRepo.toggleArchive(set)
    }

    private inner class Params(val set: String, val limit: Int, val force: Boolean)

}
