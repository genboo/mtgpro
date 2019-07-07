package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Created by gen on 03.07.2018.
 */
@Entity
data class SavedCard(@PrimaryKey
                     @NonNull var id: String,
                     var count: Int = 0, val parent: String)