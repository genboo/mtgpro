package ru.spcm.apps.mtgpro.services

import android.content.Context
import ru.spcm.apps.mtgpro.model.db.dao.SettingsDao
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.repository.BackupRepo
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class BackupSaver @Inject
constructor(private val appExecutors: AppExecutors,
            private val backupRepo: BackupRepo,
            private val settingsDao: SettingsDao) {

    fun backup(context: Context) {
        appExecutors.diskIO().execute{
            val setting = settingsDao.getSetting(Setting.Type.AUTO_BACKUP)
            if(setting != null) {
                val needBackup = setting.value.toBoolean()
                if (needBackup) {
                    backupRepo.backup(context)
                }
            }
        }
    }

}