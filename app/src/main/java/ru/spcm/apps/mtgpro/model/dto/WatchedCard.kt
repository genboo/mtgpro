package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by gen on 14.08.2018.
 */
@Entity
data class WatchedCard(@PrimaryKey
                      @NonNull var id: String)