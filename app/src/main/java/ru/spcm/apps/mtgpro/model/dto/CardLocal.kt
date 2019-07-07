package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Embedded
import androidx.room.Relation

class CardLocal {

    @Embedded
    lateinit var card: Card

    @Relation(parentColumn = "id", entityColumn = "card_id")
    lateinit var reprints: List<Reprint>

}