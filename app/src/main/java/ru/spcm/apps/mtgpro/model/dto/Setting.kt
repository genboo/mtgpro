package ru.spcm.apps.mtgpro.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(@PrimaryKey var type: Type, var value: String = "") {

    enum class Type {
        LIST_COL_SIZE,
        AUTO_BACKUP,
        VALUTE_USD_RUB,
        UPDATE_LIBRARY_CARDS_PRICE,
        SHOW_SETS_ARCHIVE,
    }

}