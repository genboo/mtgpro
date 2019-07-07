package ru.spcm.apps.mtgpro.model.dto

import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = Card::class,
        parentColumns = ["id"],
        childColumns = ["card_id"],
        onDelete = ForeignKey.CASCADE)], indices = [(Index("card_id"))])
data class Subtype(@ColumnInfo(name = "card_id")
              var cardId: String,
              var subtype: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}