package ru.spcm.apps.mtgpro.model.db.dao


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.*

@Dao
interface LibrariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Library)

    @Update
    fun update(item: Library)

    @Query("SELECT l.*, SUM(lc.count) AS count  FROM Library l LEFT JOIN LibraryCard lc ON lc.library_id = l.id GROUP BY l.id")
    fun getLibraries(): LiveData<List<LibraryInfo>>

    @Query("SELECT * FROM Library l WHERE l.id = :id")
    fun getLibrary(id: Long): LiveData<Library>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LibraryCard)

    @Query("DELETE FROM LibraryCard where library_id = :library AND card_id = :card")
    fun delete(library: Int, card: String)

    @Query("SELECT ac.cmc, ac.count count, IFNULL(acr.count, 0) creatures " +
            "FROM (SELECT c.cmc, SUM(lc.count) count " +
            "    FROM Card c, LibraryCard lc " +
            "    WHERE lc.card_id = c.id AND lc.library_id = :library AND c.cmc > 0 " +
            "    GROUP BY c.cmc) as ac " +
            "LEFT JOIN " +
            "    (SELECT c.cmc, SUM(lc.count) count " +
            "    FROM Card c, LibraryCard lc, Type t " +
            "    WHERE lc.card_id = c.id " +
            "        AND lc.library_id = :library " +
            "        AND c.cmc > 0 " +
            "        AND t.card_id = c.id " +
            "        AND t.type = 'Creature' " +
            "    GROUP BY c.cmc) as acr ON ac.cmc = acr.cmc " +
            "GROUP BY ac.cmc")
    fun getLibraryManaState(library: Long): LiveData<List<LibraryManaState>>

    @Query("SELECT cl.color, SUM(lc.count) count FROM Card c " +
            "LEFT JOIN Color cl ON c.id = cl.card_id " +
            "JOIN LibraryCard lc ON lc.card_id = c.id " +
            "WHERE lc.library_id = :library AND c.cmc > 0 " +
            "GROUP BY cl.color " +
            "ORDER BY cl.color")
    fun getLibraryColorState(library: Long): LiveData<List<LibraryColorState>>
}