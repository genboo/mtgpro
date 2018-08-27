package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.db.dao.ReportDao
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import javax.inject.Inject


/**
 * Отчет
 * Created by gen on 20.08.2018.
 */

class ReportViewModel @Inject
internal constructor(reportDao: ReportDao) : ViewModel() {

    val report: LiveData<List<CardObserved>> = reportDao.getReport()

}
