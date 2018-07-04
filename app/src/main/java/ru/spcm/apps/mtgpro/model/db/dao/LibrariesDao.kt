package ru.spcm.apps.mtgpro.model.db.dao


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo

@Dao
interface LibrariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Library)

    @Update
    fun update(item: Library)

    @Query("SELECT l.*, SUM(lc.count) AS count  FROM Library l LEFT JOIN LibraryCard lc ON lc.library_id = l.id GROUP BY l.id")
    fun getLibraries() : LiveData<List<LibraryInfo>>
}