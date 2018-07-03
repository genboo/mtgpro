package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by gen on 03.07.2018.
 */
@Entity
data class WishedCard(@PrimaryKey
                     @NonNull var id: String)