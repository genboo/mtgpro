package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao
import ru.spcm.apps.mtgpro.model.db.dao.SettingsDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.SetName
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class SettingsRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val settingsDao: SettingsDao) {

    fun getSettings(): LiveData<List<Setting>> {
        return settingsDao.getSettings()
    }

    fun updateSetting(type: Setting.Type, value: String) {
        appExecutors.diskIO().execute {
            settingsDao.insert(Setting(type, value))
        }
    }

}