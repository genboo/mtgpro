package ru.spcm.apps.mtgpro.model.db.converters

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @TypeConverter
    @JvmStatic
    fun toDate(value: String): Date {
        return dateFormatter.parse(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): String {
        return dateFormatter.format(date)
    }

}