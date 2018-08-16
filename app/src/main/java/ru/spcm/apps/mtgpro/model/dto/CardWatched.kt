package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded

class CardWatched {

    @Embedded
    lateinit var card: Card

    lateinit var price: String

}