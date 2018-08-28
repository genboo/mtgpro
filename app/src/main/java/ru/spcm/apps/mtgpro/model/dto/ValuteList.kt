package ru.spcm.apps.mtgpro.model.dto

import com.google.gson.annotations.SerializedName

class ValuteList {
    @SerializedName("USD")
    var usd: Valute? = null

    @SerializedName("EUR")
    var eur: Valute? = null
}