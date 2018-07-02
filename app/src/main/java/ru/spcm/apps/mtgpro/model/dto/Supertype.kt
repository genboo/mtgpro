package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.*

@Entity(foreignKeys = [ForeignKey(entity = Card::class,
        parentColumns = ["id"],
        childColumns = ["card_id"],
        onDelete = ForeignKey.CASCADE)], indices = [(Index("card_id"))])
class Supertype(@ColumnInfo(name = "card_id")
                var cardId: String,
                var supertype: String)  {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}