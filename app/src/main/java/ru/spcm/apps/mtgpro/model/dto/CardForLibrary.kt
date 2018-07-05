package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Embedded

class CardForLibrary() {
    @Embedded
    lateinit var card: Card

    var typeSingle: String = ""
}