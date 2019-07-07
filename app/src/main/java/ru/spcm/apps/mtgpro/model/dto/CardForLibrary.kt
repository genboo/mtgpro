package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Embedded

class CardForLibrary {
    @Embedded
    lateinit var card: Card

    var typeSingle: String = ""

    var price: Float? = 0f
}