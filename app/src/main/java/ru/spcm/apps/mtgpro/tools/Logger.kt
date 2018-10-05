package ru.spcm.apps.mtgpro.tools

import android.util.Log
import com.crashlytics.android.Crashlytics
import ru.spcm.apps.mtgpro.BuildConfig

object Logger {

    private const val DEFAULT_LOGGER = BuildConfig.APPLICATION_ID

    fun e(tag: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }else{
            Crashlytics.log(Log.ERROR, tag, message)
        }
    }

    fun e(tag: String, message: Exception) {
        e(tag, message.message)
    }

    fun e(message: Exception) {
        e(DEFAULT_LOGGER, message.message)
    }

    fun e(message: String?) {
        e(DEFAULT_LOGGER, message)
    }

    fun d(tag: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun d(tag: String, message: Exception) {
        d(tag, message.message)
    }

    fun d(message: Exception) {
        d(DEFAULT_LOGGER, message.message)
    }

    fun d(message: String) {
        d(DEFAULT_LOGGER, message)
    }
}
