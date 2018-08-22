package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.repository.CacheTypeRepo
import ru.spcm.apps.mtgpro.repository.SettingsRepo

import javax.inject.Inject


/**
 * Отчет
 * Created by gen on 20.08.2018.
 */

class MainViewModel @Inject
internal constructor(settingsRepo: SettingsRepo, private val cacheTypeRepo: CacheTypeRepo) : ViewModel() {

    val settings: LiveData<List<Setting>> = settingsRepo.getSettings()

    fun updateCacheType(){
        cacheTypeRepo.updateCacheType()
    }

}
