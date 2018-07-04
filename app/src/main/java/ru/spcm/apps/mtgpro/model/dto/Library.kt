package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Library(var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}