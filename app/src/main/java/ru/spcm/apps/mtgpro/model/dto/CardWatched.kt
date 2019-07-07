package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Embedded

class CardWatched {

    @Embedded
    lateinit var card: Card

    var price: Float = 0f

}