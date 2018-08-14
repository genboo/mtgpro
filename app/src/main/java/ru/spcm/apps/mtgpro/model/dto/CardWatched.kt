package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CardWatched {

    @Embedded
    lateinit var card: Card

    lateinit var price: String

}