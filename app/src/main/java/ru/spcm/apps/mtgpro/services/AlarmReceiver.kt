package ru.spcm.apps.mtgpro.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.PriceUpdateDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.tools.AppExecutors
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var priceUpdateDao: PriceUpdateDao

    @Inject
    lateinit var scryCardDao: ScryCardDao

    @Inject
    lateinit var scryCardApi: ScryCardApi

    var component: AppComponent? = null

    override fun onReceive(context: Context, intent: Intent) {
        component = (context.applicationContext as App).appComponent
        component?.inject(this)

        val viewModel = PriceUpdater(appExecutors, priceUpdateDao, scryCardDao, scryCardApi)
        viewModel.update()
    }
}