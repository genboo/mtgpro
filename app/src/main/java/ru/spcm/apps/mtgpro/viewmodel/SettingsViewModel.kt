package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import ru.spcm.apps.mtgpro.model.dto.Setting
import javax.inject.Inject
import ru.spcm.apps.mtgpro.repository.BackupRepo
import ru.spcm.apps.mtgpro.repository.SettingsRepo


/**
 * Настройки
 * Created by gen on 11.01.2018.
 */

class SettingsViewModel @Inject
internal constructor(private val backupRepo: BackupRepo,
                     private val settingsRepo: SettingsRepo) : ViewModel() {

    fun updateAutoBackup(flag: Boolean) {
        settingsRepo.updateSetting(Setting.Type.AUTO_BACKUP, flag.toString())
    }

    fun backup(context: Context): LiveData<Boolean> {
        return backupRepo.backup(context)
    }

    fun restore(context: Context): LiveData<Boolean> {
        return backupRepo.restore(context)
    }

}
