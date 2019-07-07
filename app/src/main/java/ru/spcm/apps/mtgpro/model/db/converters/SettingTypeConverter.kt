package ru.spcm.apps.mtgpro.model.db.converters

import androidx.room.TypeConverter
import ru.spcm.apps.mtgpro.model.dto.Setting

object SettingTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toType(value: String): Setting.Type {
        return Setting.Type.valueOf(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromType(type: Setting.Type): String {
        return type.name
    }
}