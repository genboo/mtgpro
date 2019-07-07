package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Ignore

data class GraphDot(var date: String = "",
                    var count: Float = 0f) {

    @Ignore
    constructor() : this("", 0f)
}