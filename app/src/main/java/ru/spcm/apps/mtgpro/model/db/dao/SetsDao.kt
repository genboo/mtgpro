package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.dto.SetName

/**
 * Получение данных по сетам
 * Created by gen on 07.05.2018.
 */
@Dao
interface SetsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Set): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<Set>)

    @Delete
    fun delete(item: Set)

    @Update
    fun update(item: Set)

    @Query("DELETE FROM `Set`")
    fun clear()

    @Query("SELECT * FROM `Set` WHERE archive = 0 ORDER BY archive ASC, releaseDate DESC")
    fun getSets(): LiveData<List<Set>>

    @Query("SELECT * FROM `Set` ORDER BY archive ASC, releaseDate DESC")
    fun getSetsWithArchive(): LiveData<List<Set>>

    @Query("SELECT * FROM `Set` WHERE code = :code")
    fun getSet(code: String): LiveData<Set>

    @Query("SELECT c.`set`, c.setTitle FROM Card c, WishedCard w WHERE c.id = w.id GROUP BY c.`set` ORDER BY c.setTitle")
    fun getWishedSetNames(): LiveData<List<SetName>>
}
