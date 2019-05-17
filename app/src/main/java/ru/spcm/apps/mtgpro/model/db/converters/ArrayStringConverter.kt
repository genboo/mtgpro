package ru.spcm.apps.mtgpro.model.db.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.spcm.apps.mtgpro.model.dto.Setting

object ArrayStringConverter {
    @TypeConverter
    @JvmStatic
    fun toArray(value: String?): Array<String> {
        if(value == null) return arrayOf()
        return Gson().fromJson(value, Array<String>::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun fromArray(array: Array<String>?): String {
        return Gson().toJson(array)
    }
}