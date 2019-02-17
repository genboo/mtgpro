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
    lateinit var watchUpdater: WatchedCardsPriceUpdater
    @Inject
    lateinit var librariesUpdater: LibrariesCardsPriceUpdater

    @Inject
    lateinit var backupSaver: BackupSaver

    @Inject
    lateinit var valuteUpdater: ValuteUpdater
    @Inject
    lateinit var convertCollection: ConvertCollection

    private var component: AppComponent? = null

    override fun onReceive(context: Context, intent: Intent) {
        component = (context.applicationContext as App).appComponent
        component?.inject(this)

        if (intent.getBooleanExtra(CONVERT, false)) {
            convertCollection.result.observeForever(object : Observer<Int> {
                override fun onChanged(data: Int?) {
                    if (data == null) return
                    if (data >= 35000) convertCollection.result.removeObserver(this)
                    val message = context.getString(R.string.notify_convert_progress, data)
                    showNotification(context, NOTIFY_CONVERT, context.getString(R.string.app_name), message, message, false)
                }
            })
            convertCollection.convert()
            showNotification(context, NOTIFY_CONVERT, context.getString(R.string.app_name),
                    context.getString(R.string.notify_convert_notification),
                    context.getString(R.string.notify_convert_notification),
                    true)
            return
        }

        if (!intent.getBooleanExtra(FORCE, false)) {
            backupSaver.backup(context)
        }

        valuteUpdater.update()

        showNotification(context, NOTIFY_WATCHED, context.getString(R.string.app_name),
                context.getString(R.string.notify_update_notification),
                context.getString(R.string.notify_update_notification),
                true)

        watchUpdater.result.observeForever(object : Observer<UpdateResult> {
            override fun onChanged(data: UpdateResult?) {
                if (data != null) {
                    if (data.currentCard == 0) {
                        watchUpdater.result.removeObserver(this)
                        val message = context.getString(R.string.notify_update_complete, data.allCount, data.updatedCount)
                        showNotification(context, NOTIFY_WATCHED, context.getString(R.string.app_name), context.getString(R.string.notify_update_complete_annotation), message)
                    } else {
                        val message = context.getString(R.string.notify_update_progress, data.currentCard, data.allCount)
                        showNotification(context, NOTIFY_WATCHED, context.getString(R.string.app_name), context.getString(R.string.notify_update_progress_annotation), message, true)
                    }
                }
            }
        })
        watchUpdater.update()

        librariesUpdater.result.observeForever(object : Observer<UpdateResult> {
            override fun onChanged(data: UpdateResult?) {
                if (data != null) {
                    if (data.currentCard == 0) {
                        librariesUpdater.result.removeObserver(this)
                        val message = context.getString(R.string.notify_update_complete, data.allCount, data.updatedCount)
                        showNotification(context, NOTIFY_LIBRARIES, context.getString(R.string.app_name), context.getString(R.string.notify_update_complete_annotation), message)
                    } else {
                        val message = context.getString(R.string.notify_update_progress, data.currentCard, data.allCount)
                        showNotification(context, NOTIFY_LIBRARIES, context.getString(R.string.app_name), context.getString(R.string.notify_update_progress_annotation), message, true)
                    }
                }
            }
        })
        librariesUpdater.update()

    }

    private fun showNotification(context: Context, id: Int, title: String, annotation: String, message: String, permanent: Boolean = false) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(LAUNCH_FRAGMENT, LAUNCH_FRAGMENT_REPORT)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, App.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(annotation)
                .setVibrate(null)
                .setSmallIcon(R.drawable.ic_black_mana)
                .setSound(null)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
        if (permanent) {
            builder.setOngoing(true)
        }
        val notification = builder.build()
        if (permanent) {
            notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
        }
        notificationManager.notify(id, notification)
    }

    companion object {
        const val LAUNCH_FRAGMENT = "launch_fragment"
        const val LAUNCH_FRAGMENT_REPORT = "launch_fragment_report"
        const val FORCE = "force"
        const val CONVERT = "convert"

        const val NOTIFY_WATCHED = 1
        const val NOTIFY_LIBRARIES = 2
        const val NOTIFY_CONVERT = 3
    }

}