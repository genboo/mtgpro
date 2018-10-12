package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index(value = ["set", "number"])])
data class ScryCard(@PrimaryKey var id: String) {
    var usd: String = ""

    var eur: String = ""

    var set: String = ""

    @SerializedName("collector_number")
    var number: String = ""


    fun prepare(){
        if (eur == null) {
            eur = ""
        }
        if (usd == null) {
            usd = ""
        }

        usd = usd.replace("$", "")
        eur = eur.replace("€", "")
    }
}