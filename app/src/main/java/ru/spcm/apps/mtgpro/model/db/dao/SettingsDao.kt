package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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