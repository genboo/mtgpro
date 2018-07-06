package ru.spcm.apps.mtgpro.model.dto

class LibraryData{
    var cards: List<CardForLibrary>? = null
    var manaState: List<LibraryManaState>? = null
    var colorState: List<LibraryColorState>? = null

    fun isFull(): Boolean {
        return cards != null && manaState != null && colorState != null
    }
}