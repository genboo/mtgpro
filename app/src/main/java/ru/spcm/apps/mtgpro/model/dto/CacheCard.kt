package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

/**
 * Created by gen on 29.06.2018.
 */
@Entity(indices = [(Index("cache_key"))])
data class CacheCard(@PrimaryKey
                     @SerializedName("multiverseid")
                     @NonNull var id : String){

    @ColumnInfo(name = "cache_key")
    var cacheKey: String = ""
}