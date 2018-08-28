package ru.spcm.apps.mtgpro.tools

import ru.spcm.apps.mtgpro.model.dto.Setting

class Settings {

    private var settings = HashMap<Setting.Type, String>()

    fun getString(key: Setting.Type, default: String): String {
        return settings[key] ?: default
    }

    fun getInt(key: Setting.Type, default: Int): Int {
        return settings[key]?.toInt() ?: default
    }

    fun getBoolean(key: Setting.Type, default: Boolean): Boolean {
        return settings[key]?.toBoolean() ?: default
    }

    private fun getFloat(key: Setting.Type, default: Float): Float {
        return settings[key]?.toFloat() ?: default
    }

    fun getCurrentValute(): Float {
        return getFloat(Setting.Type.VALUTE_USD_RUB, 1f)
    }

    fun updateSettings(data: List<Setting>) {
        data.forEach {
            settings[it.type] = it.value
        }
    }
}