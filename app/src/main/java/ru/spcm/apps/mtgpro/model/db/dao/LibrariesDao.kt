package ru.spcm.apps.mtgpro.model.db.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import ru.spcm.apps.mtgpro.model.dto.*

@Dao
interface LibrariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Library)

    @Update
    fun update(item: Library)

    @Delete
    fun delete(item: Library)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT l.*, sum(lc.count) AS count, " +
            "CASE WHEN SUBSTR(c.number, -1) IN ('a', 'b') THEN SUBSTR(c.number, 1, length(c.number) - 1) ELSE c.number END num, " +
            "SUM(sc.usd * lc.count) * :valute AS price " +
            "FROM Library l " +
            "LEFT JOIN LibraryCard lc ON lc.library_id = l.id " +
            "LEFT JOIN Card c ON lc.card_id = c.id " +
            "LEFT JOIN ScryCard sc ON sc.number = num AND sc.`set` = LOWER(c.`set`) " +
            "GROUP BY l.id")
    fun getLibraries(valute: Float): LiveData<List<LibraryInfo>>

    @Query("SELECT * FROM Library l WHERE l.id = :id")
    fun getLibrary(id: Long): LiveData<Library>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LibraryCard)

    @Query("DELETE FROM LibraryCard where library_id = :library AND card_id = :card")
    fun delete(library: Long, card: String)

    @Query("SELECT ac.cmc, ac.count count, IFNULL(acr.count, 0) creatures " +
            "FROM (SELECT c.cmc, SUM(lc.count) count " +
            "    FROM Card c, LibraryCard lc " +
            "    WHERE lc.card_id = c.id AND lc.library_id = :library AND c.cmc > 0 AND c.cmc <> '0' " +
            "    GROUP BY c.cmc) as ac " +
            "LEFT JOIN " +
            "    (SELECT c.cmc, SUM(lc.count) count " +
            "    FROM Card c, LibraryCard lc, Type t " +
            "    WHERE lc.card_id = c.id " +
            "        AND lc.library_id = :library " +
            "        AND c.cmc > 0 AND c.cmc <> '0' " +
            "        AND t.card_id = c.id " +
            "        AND t.type = 'Creature' " +
            "    GROUP BY c.cmc) as acr ON ac.cmc = acr.cmc " +
            "GROUP BY ac.cmc")
    fun getLibraryManaState(library: Long): LiveData<List<LibraryManaState>>

    @Query("SELECT cl.color, SUM(lc.count) count FROM Card c " +
            "LEFT JOIN Color cl ON c.id = cl.card_id " +
            "JOIN LibraryCard lc ON lc.card_id = c.id " +
            "WHERE lc.library_id = :library AND c.cmc > 0  AND c.cmc <> '0'" +
            "GROUP BY cl.color " +
            "ORDER BY cl.color")
    fun getLibraryColorState(library: Long): LiveData<List<LibraryColorState>>

    @Query("SELECT l.name, lc.library_id as id, lc.count, '' as price " +
            "FROM LibraryCard lc, Library l " +
            "WHERE lc.library_id = l.id AND lc.card_id = :card")
    fun getLibrariesByCard(card: String): LiveData<List<LibraryInfo>>

    @Query("SELECT c.* FROM LibraryCard lc LEFT JOIN Card c ON lc.card_id = c.id GROUP BY c.id")
    fun getCardsInAllLibraries(): List<Card>
}