package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Created by gen on 03.07.2018.
 */
@Entity
data class WishedCard(@PrimaryKey
                     @NonNull var id: String)