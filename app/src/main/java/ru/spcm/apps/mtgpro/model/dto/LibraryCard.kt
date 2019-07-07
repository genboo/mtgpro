package ru.spcm.apps.mtgpro.model.dto

import androidx.room.*

@Entity(indices = [
    Index(value = ["library_id", "card_id"], unique = true),
    Index(value = ["card_id"]),
    Index(value = ["library_id"])],
        foreignKeys = [
            ForeignKey(entity = Card::class,
                    parentColumns = ["id"],
                    childColumns = ["card_id"],
                    onDelete = ForeignKey.NO_ACTION),
            ForeignKey(entity = Library::class,
                    parentColumns = ["id"],
                    childColumns = ["library_id"],
                    onDelete = ForeignKey.CASCADE)
        ])
data class LibraryCard(
        @ColumnInfo(name = "library_id")
        val libraryId: Long,
        @ColumnInfo(name = "card_id")
        var cardId: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var count: Int = 0
}