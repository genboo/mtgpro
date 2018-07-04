package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo

class LibraryInfo{
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "count")
    var cardsCount: Int = 0
}