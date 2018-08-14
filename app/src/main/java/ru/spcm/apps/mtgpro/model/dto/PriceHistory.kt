package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by gen on 14.08.2018.
 */
@Entity(indices = [Index("date"), Index(value = ["card_id", "date"], unique = true)])
data class PriceHistory(@ColumnInfo(name = "card_id")
                        var cardId: String,
                        val price: String){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var date: Date = Date()
}