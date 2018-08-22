package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Setting(@PrimaryKey var type: Type, var value: String = "") {

    enum class Type {
        LIST_COL_SIZE,
        AUTO_BACKUP,
    }

}