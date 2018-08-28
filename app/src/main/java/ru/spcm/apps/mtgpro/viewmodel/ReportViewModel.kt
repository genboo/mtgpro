package ru.spcm.apps.mtgpro.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ru.spcm.apps.mtgpro.model.db.dao.ReportDao
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.tools.AbsentLiveData
import javax.inject.Inject


/**
 * Отчет
 * Created by gen on 20.08.2018.
 */

class ReportViewModel @Inject
internal constructor(reportDao: ReportDao) : ViewModel() {

    private val report: LiveData<List<CardObserved>>
    private val switch: MutableLiveData<Float> = MutableLiveData()

    init {
        report = Transformations.switchMap(switch) {
            if (it == null) {
                return@switchMap AbsentLiveData.create<List<CardObserved>>()
            }
            return@switchMap reportDao.getReport(it)
        }
    }

    fun getReport(): LiveData<List<CardObserved>> {
        return report
    }

    fun loadReport(valute: Float) {
        switch.postValue(valute)
    }


}
