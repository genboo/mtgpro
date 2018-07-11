package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by gen on 29.06.2018.
 */
@Entity(indices = [Index("cache_key"), Index(value = ["card_id", "cache_key"],
        unique = true)])
data class CacheCard(@ColumnInfo(name = "card_id")
                     var cardId: String = "",
                     @ColumnInfo(name = "cache_key")
                     var cacheKey: String = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}