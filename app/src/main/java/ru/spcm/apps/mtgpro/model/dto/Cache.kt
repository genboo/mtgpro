package ru.spcm.apps.mtgpro.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("cache_key")])
data class Cache(@PrimaryKey
                 @ColumnInfo(name = "cache_key")
                 val cacheKey: String,
                 val expire: Long)