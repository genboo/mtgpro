package ru.spcm.apps.mtgpro.services

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import ru.spcm.apps.mtgpro.model.db.dao.SettingsDao
import ru.spcm.apps.mtgpro.repository.BackupRepo
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class ConvertCollection @Inject
constructor(private val appExecutors: AppExecutors,
            private val backupRepo: BackupRepo,
            private val settingsDao: SettingsDao) {

    val result = MutableLiveData<Int>()

    fun convert(context: Context) {
        appExecutors.diskIO().execute {
            result.postValue(35000)
        }
    }

}