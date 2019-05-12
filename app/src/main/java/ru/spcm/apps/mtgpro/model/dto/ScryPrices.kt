package ru.spcm.apps.mtgpro.model.dto

import com.google.gson.annotations.SerializedName

data class ScryPrices(
        val usd: String,
        @SerializedName("usd_foil")
        val foil: String,
        val eur: String)