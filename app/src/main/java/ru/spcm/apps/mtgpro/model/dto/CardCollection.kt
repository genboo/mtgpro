package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded

class CardCollection {
    @Embedded
    lateinit var card: Card

    var price: Float? = 0f
}