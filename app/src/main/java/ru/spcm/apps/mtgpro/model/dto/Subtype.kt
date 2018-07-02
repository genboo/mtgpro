package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.*

@Entity(foreignKeys = [ForeignKey(entity = Card::class,
        parentColumns = ["id"],
        childColumns = ["card_id"],
        onDelete = ForeignKey.CASCADE)], indices = [(Index("card_id"))])
class Subtype(@ColumnInfo(name = "card_id")
              var cardId: String,
              var subtype: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}