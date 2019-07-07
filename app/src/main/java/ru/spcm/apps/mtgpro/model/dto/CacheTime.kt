package ru.spcm.apps.mtgpro.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("cache_type")])
data class CacheTime(@PrimaryKey
                     @ColumnInfo(name = "cache_type")
                     val cacheType: String,
                     val time: Long)