package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(indices = [Index("cache_type")])
data class CacheTime(@PrimaryKey
                     @ColumnInfo(name = "cache_type")
                     val cacheType: String,
                     val time: Int)