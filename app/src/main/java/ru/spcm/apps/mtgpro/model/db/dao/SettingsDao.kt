package ru.spcm.apps.mtgpro.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.spcm.apps.mtgpro.model.dto.Setting

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Setting)

    @Query("SELECT * FROM Setting")
    fun getSettings(): LiveData<List<Setting>>

    @Query("SELECT * FROM Setting s WHERE s.type = :type")
    fun getSetting(type: Setting.Type): Setting?

}