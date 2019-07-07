package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("releaseDate")])
data class Set(@PrimaryKey var code: String) {

    var name: String = ""

    var border: String? = ""

    var releaseDate: String = ""

    var block: String? = ""

    var archive: Boolean = false

}