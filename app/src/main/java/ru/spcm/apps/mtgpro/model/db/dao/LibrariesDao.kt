package ru.spcm.apps.mtgpro.model.db.dao


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryCard
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo

@Dao
interface LibrariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Library)

    @Update
    fun update(item: Library)

    @Query("SELECT l.*, SUM(lc.count) AS count  FROM Library l LEFT JOIN LibraryCard lc ON lc.library_id = l.id GROUP BY l.id")
    fun getLibraries() : LiveData<List<LibraryInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LibraryCard)

    @Query("DELETE FROM LibraryCard where library_id = :library AND card_id = :card")
    fun delete(library: Int, card: String)
}