package ru.spcm.apps.mtgpro.model.dto

import androidx.room.ColumnInfo
import androidx.room.Ignore

class LibraryInfo {
    var id: Long = 0

    var name: String? = null

    @ColumnInfo(name = "count")
    var cardsCount: Int = 0

    var price: Float? = 0f

    @Ignore
    var colors: List<LibraryColorState> = emptyList()
}