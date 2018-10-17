package ru.spcm.apps.mtgpro.services

import ru.spcm.apps.mtgpro.model.api.ValuteApi
import ru.spcm.apps.mtgpro.model.db.dao.SettingsDao
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class ValuteUpdater @Inject
constructor(private val appExecutors: AppExecutors,
            private val valuteApi: ValuteApi,
            private val settingsDao: SettingsDao) {

    fun update() {
        appExecutors.networkIO().execute {
            synchronized(appExecutors) {
                val valuteList = valuteApi.getValutes().execute().body()
                val usd = valuteList?.usd
                if (usd != null) {
                    settingsDao.insert(Setting(Setting.Type.VALUTE_USD_RUB, usd.value))
                }
            }
        }
    }
}