package ru.spcm.apps.mtgpro.services

import android.app.Notification
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
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var controller: PriceUpdater

    @Inject
    lateinit var backupSaver: BackupSaver

    var component: AppComponent? = null

    override fun onReceive(context: Context, intent: Intent) {
        component = (context.applicationContext as App).appComponent
        component?.inject(this)
        if (!intent.getBooleanExtra(FORCE, false)) {
            backupSaver.backup(context)
        }

        showNotification(context, context.getString(R.string.app_name),
                context.getString(R.string.notify_update_notification),
                context.getString(R.string.notify_update_notification),
                true)

        val updateData = controller.update()
        updateData.observeForever(object : Observer<UpdateResult> {
            override fun onChanged(data: UpdateResult?) {
                if (data != null) {
                    if (data.currentCard == 0) {
                        updateData.removeObserver(this)
                        val message = context.getString(R.string.notify_update_complete, data.allCount, data.updatedCount)
                        showNotification(context, context.getString(R.string.app_name), context.getString(R.string.notify_update_complete_annotation), message)
                    } else {
                        val message = context.getString(R.string.notify_update_progress, data.currentCard, data.allCount)
                        showNotification(context, context.getString(R.string.app_name), context.getString(R.string.notify_update_progress_annotation), message, true)
                    }
                }
            }
        })
    }

    private fun showNotification(context: Context, title: String, annotation: String, message: String, permanent: Boolean = false) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(LAUNCH_FRAGMENT, LAUNCH_FRAGMENT_REPORT)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, App.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(annotation)
                .setVibrate(null)
                .setSmallIcon(R.drawable.ic_black_mana)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
        if (permanent) {
            builder.setOngoing(true)
        }
        val notification = builder.build()
        if (permanent) {
            notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
        }
        notificationManager.notify(1, notification)
    }

    companion object {
        const val LAUNCH_FRAGMENT = "launch_fragment"
        const val LAUNCH_FRAGMENT_REPORT = "launch_fragment_report"
        const val FORCE = "force"
    }

}