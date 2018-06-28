package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * Получение данных по сетам
 * Created by gen on 07.05.2018.
 */
@Dao
interface SetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Set): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Set>)

    @Delete
    fun delete(item: Set)

    @Update
    fun update(item: Set)

    @Query("DELETE FROM `Set`")
    fun clear()

    @Query ("SELECT * FROM `Set` ORDER BY releaseDate DESC")
    fun getSets() : LiveData<List<Set>>
}
