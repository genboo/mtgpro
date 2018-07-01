package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by gen on 29.06.2018.
 */
@Entity(indices = [(Index("cache_key")), Index(value = ["id", "cache_key"],
        unique = true)])
data class CacheCard(@PrimaryKey
                     @NonNull var id: String,
                     @ColumnInfo(name = "cache_key")
                     var cacheKey: String = "")