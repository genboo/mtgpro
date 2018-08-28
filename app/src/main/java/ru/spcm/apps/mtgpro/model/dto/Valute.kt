package ru.spcm.apps.mtgpro.model.dto

import com.google.gson.annotations.SerializedName

class Valute {
    @SerializedName("ID")
    var id: String = ""

    @SerializedName("Name")
    var name: String = ""

    @SerializedName("CharCode")
    var code: String = ""

    @SerializedName("Value")
    var value: String = ""
}