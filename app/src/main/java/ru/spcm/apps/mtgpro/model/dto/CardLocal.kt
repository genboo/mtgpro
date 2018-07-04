package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CardLocal {

    @Embedded
    lateinit var card: Card

    @Relation(parentColumn = "id", entityColumn = "card_id")
    lateinit var reprints: List<Reprint>

}