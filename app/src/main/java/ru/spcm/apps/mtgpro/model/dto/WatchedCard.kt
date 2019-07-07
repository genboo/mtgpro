package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Created by gen on 14.08.2018.
 */
@Entity
data class WatchedCard(@PrimaryKey
                       @NonNull var id: String) {

    var observe: Boolean = false
    var top: Float = 0f
    var bottom: Float = 0f
}