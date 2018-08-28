package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded

class CardWatched {

    @Embedded
    lateinit var card: Card

    var price: Float = 0f

}