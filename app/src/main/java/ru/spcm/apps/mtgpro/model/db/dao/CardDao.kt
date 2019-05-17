package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.*

/**
 * Сохраненные карты
 * Created by gen on 29.06.2018.
 */
@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Card): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Card>)

    @Delete
    fun delete(item: Card)

    @Update
    fun update(item: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: SavedCard): Long

    @Delete
    fun delete(item: SavedCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: WishedCard): Long

    @Delete
    fun delete(item: WishedCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: WatchedCard): Long

    @Update
    fun update(item: WatchedCard)

    @Delete
    fun delete(item: WatchedCard)

    @Query("SELECT c.*, sc.count FROM CacheCard cc LEFT JOIN Card c ON c.id = cc.card_id LEFT JOIN SavedCard sc ON sc.id = c.id WHERE cc.cache_key = :cacheKey ORDER BY c.numberFormatted LIMIT :limit")
    fun getCachedCards(cacheKey: String, limit: Int): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count, sc.parent FROM Card c LEFT JOIN SavedCard sc ON sc.id = c.id WHERE c.id = :id OR sc.parent = :id ORDER BY sc.parent ASC")
    fun getSavedCards(id: String): LiveData<List<Card>>

    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getCards(mid: String): LiveData<List<Card>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, sc.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc,  c.manaCost, sc.count, " +
            "(CASE WHEN scc.usd IS NULL THEN 0 ELSE scc.usd END) * :valute AS price  " +
            "FROM SavedCard sc " +
            "LEFT JOIN Card c ON c.id = sc.id " +
            "LEFT JOIN ScryCard scc ON scc.number = c.number AND scc.`set` = lower(c.`set`) " +
            "WHERE sc.parent = '' " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getAllCards(valute: Float): DataSource.Factory<Int, CardCollection>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, sc.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.manaCost, sc.count," +
            "(CASE WHEN scc.usd IS NULL THEN 0 ELSE scc.usd END) * :valute AS price  " +
            " FROM SavedCard sc " +
            "LEFT JOIN Card c ON c.id = sc.id " +
            "LEFT JOIN ScryCard scc ON scc.number = c.number AND scc.`set` = lower(c.`set`) " +
            "LEFT JOIN Type type ON c.id = type.card_id " +
            "LEFT JOIN Subtype sub ON c.id = sub.card_id " +
            "LEFT JOIN Color col ON col.card_id = c.id " +
            "WHERE " +
            "    (sub.subtype IN (:subtypes)  OR (sub.subtype is NULL AND (type.type NOT IN ('Instant', 'Enchantment', 'Sorcery') OR type.type IN (:types)))) " +
            "    AND type.type IN (:types)" +
            "    AND (col.color IN (:colors) OR col.color is null) " +
            "    AND c.rarity IN (:rarities) " +
            "    AND c.`set` IN (:sets) " +
            "    AND sc.parent = '' " +
            "GROUP BY c.id " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getFilteredCards(valute: Float, types: Array<String>, subtypes: Array<String>, colors: Array<String>,
                         rarities: Array<String>, sets: Array<String>): DataSource.Factory<Int, CardCollection>

    @Query("SELECT * FROM WishedCard c WHERE c.id = :id")
    fun getWish(id: String): LiveData<WishedCard>

    @Query("SELECT c.*, sc.count FROM WishedCard wc LEFT JOIN Card c ON c.id = wc.id LEFT JOIN SavedCard sc ON sc.id = wc.id  WHERE c.id IS NOT NULL ORDER BY c.setTitle, c.numberFormatted")
    fun getWishedCards(): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM WishedCard wc LEFT JOIN Card c ON c.id = wc.id LEFT JOIN SavedCard sc ON sc.id = wc.id WHERE c.`set` IN (:sets) ORDER BY c.setTitle, c.numberFormatted")
    fun getWishedCardsFiltered(sets: Array<String>): LiveData<List<Card>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.*, lc.count, " +
            " MIN(t.type) typeSingle, " +
            " CASE WHEN SUBSTR(c.number, -1) IN ('a', 'b') THEN SUBSTR(c.number, 1, length(c.number) - 1) ELSE c.number END num, " +
            " (CASE WHEN sc.usd IS NULL THEN 0 ELSE sc.usd END) * :valute AS price  " +
            " FROM Card c, LibraryCard lc, Type t " +
            " LEFT JOIN ScryCard sc ON sc.number = num AND sc.`set` = lower(c.`set`) " +
            " WHERE lc.library_id = :library AND lc.card_id = c.id AND t.card_id = c.id " +
            " GROUP BY c.id " +
            " ORDER BY typeSingle, c.setTitle, numberFormatted")
    fun getCardsInLibrary(library: Long, valute: Float): LiveData<List<CardForLibrary>>

    @Query("UPDATE SavedCard SET parent = :parent WHERE id = :id")
    fun updateLink(id: String, parent: String)

    @Query("SELECT * FROM Card c WHERE c.id = :id")
    fun getCardById(id: String): Card

    @Query("SELECT * FROM Card c WHERE c.`set` = :set AND c.number = :number")
    fun getCardBySetAndNumber(set: String, number: String): Card?

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, c.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.manaCost, c.count, c.number, " +
            "(CASE WHEN sc.usd IS NULL THEN 0 ELSE sc.usd END) * :valute AS price  " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "LEFT JOIN ScryCard sc ON sc.number = c.number AND sc.`set` = lower(c.`set`) " +
            "WHERE c.id IS NOT NULL " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getWatchedCards(valute: Float): DataSource.Factory<Int, CardWatched>

    @Query("SELECT c.id, c.imageUrl, wc.observe, wc.top * :valute as top , wc.bottom * :valute as bottom, " +
            "CASE WHEN r.diff IS NULL THEN 0 ELSE r.diff * :valute END as diff, " +
            "(SELECT price FROM PriceHistory pc WHERE pc.card_id = c.id ORDER BY date DESC) * :valute as price, " +
            "(SELECT MAX(CAST(price AS FLOAT)) AS max FROM PriceHistory pc WHERE pc.card_id = c.id) * :valute as max, " +
            "(SELECT MIN(CAST(price AS FLOAT)) AS min FROM PriceHistory pc WHERE pc.card_id = c.id) * :valute as min " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Report r ON r.card_id = wc.id " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "WHERE wc.id = :id")
    fun getObservedCard(id: String, valute: Float): LiveData<CardObserved>


    @Query("SELECT c.*, sc.count FROM Card c JOIN SavedCard sc ON sc.id = c.id WHERE c.`set` = :set AND c.numberFormatted = :number AND c.nameOrigin = :name")
    fun getCardSaved(set: String, number: String, name: String): Card?

    @Query("UPDATE SavedCard SET id = :id WHERE id = :old")
    fun updateSaved(id: String, old: String)

    @Query("UPDATE SavedCard SET parent = :parent WHERE parent = :old")
    fun updateParent(parent: String, old: String)

    @Query("UPDATE Color SET card_id = :id WHERE card_id = :old")
    fun updateColors(id: String, old: String)

    @Query("UPDATE Subtype SET card_id = :id WHERE card_id = :old")
    fun updateSubtypes(id: String, old: String)

    @Query("UPDATE Supertype SET card_id = :id WHERE card_id = :old")
    fun updateSupertypes(id: String, old: String)

    @Query("UPDATE Type SET card_id = :id WHERE card_id = :old")
    fun updateTypes(id: String, old: String)

    @Query("UPDATE WishedCard SET id = :id WHERE id = :old")
    fun updateWish(id: String, old: String)

    @Query("UPDATE WatchedCard SET id = :id WHERE id = :old")
    fun updateWatched(id: String, old: String)

    @Query("UPDATE LibraryCard SET card_id = :id WHERE card_id = :old")
    fun updateLibrary(id: String, old: String)

    @Query("UPDATE Report SET card_id = :id WHERE card_id = :old")
    fun updateReport(id: String, old: String)

    @Query("DELETE FROM Card WHERE `set` = :set AND numberFormatted = :number AND nameOrigin = :name AND id <> :id")
    fun clearOld(id: String, set: String, number: String, name: String)

    @Query("DELETE FROM CacheCard")
    fun clearCached()

    @Query("DELETE FROM Cache")
    fun clearCache()
}