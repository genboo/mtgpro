package ru.spcm.apps.mtgpro

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.crashlytics.android.Crashlytics
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import ru.spcm.apps.mtgpro.di.components.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.services.AlarmReceiver
import java.util.*

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        val debug = false
        val picasso = Picasso.Builder(this)
                .downloader(OkHttp3Downloader(this, 750000000))
                .indicatorsEnabled(debug)
                .loggingEnabled(debug)
                .build()
        Picasso.setSingletonInstance(picasso)
        Fabric.with(this, Crashlytics())

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 1)
            set(Calendar.MINUTE, 0)
            if(this.before(Calendar.getInstance())){
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent)
    }

}