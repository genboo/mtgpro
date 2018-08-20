package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by gen on 20.08.2018.
 */
@Entity(indices = [Index("card_id")])
data class Report(@ColumnInfo(name = "card_id")
                        var cardId: String,
                  val diff: String){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}