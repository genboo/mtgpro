package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Library(var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}