package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index(value = ["set", "number"])])
data class ScryCard(@PrimaryKey var id: String) {
    var usd: String = ""

    var eur: String = ""

    var set: String = ""

    @SerializedName("collector_number")
    var number: String = ""

    @Ignore
    var prices: ScryPrices? = null

    fun prepare() {
        prices?.let {
            eur = it.eur
            usd = if(it.usd.isEmpty()) it.foil else it.usd
        }

        usd = usd.replace("$", "")
        eur = eur.replace("€", "")
    }
}