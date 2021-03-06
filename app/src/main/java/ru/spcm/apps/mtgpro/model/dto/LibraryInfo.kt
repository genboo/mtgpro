package ru.spcm.apps.mtgpro.model.dto

import androidx.room.ColumnInfo

class LibraryInfo {
    var id: Long = 0

    var name: String? = null

    @ColumnInfo(name = "count")
    var cardsCount: Int = 0

    var price: Float? = 0f
}