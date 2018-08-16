package ru.spcm.apps.mtgpro.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.PriceUpdateDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.view.activities.MainActivity
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
        val updateData = viewModel.update()
        updateData.observeForever(object : Observer<UpdateResult> {
            override fun onChanged(data: UpdateResult?) {
                updateData.removeObserver(this)
                if (data != null) {
                    val message = String.format(context.getString(R.string.notify_update_complete, data.allCount, data.updatedCount))
                    showNotification(context, context.getString(R.string.app_name), message)
                }
            }
        })
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, App.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_black_mana)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        notificationManager.notify(1, builder.build())
    }

}