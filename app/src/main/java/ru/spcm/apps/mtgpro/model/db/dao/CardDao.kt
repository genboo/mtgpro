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

    @Delete
    fun delete(item: WatchedCard)

    @Query("SELECT c.*, sc.count FROM CacheCard cc LEFT JOIN Card c ON c.id = cc.card_id LEFT JOIN SavedCard sc ON sc.id = c.id WHERE cc.cache_key = :cacheKey ORDER BY c.numberFormatted LIMIT :limit")
    fun getCachedCards(cacheKey: String, limit: Int): LiveData<List<Card>>

    @Transaction
    @Query("SELECT c.*, sc.count, sc.parent FROM Card c LEFT JOIN SavedCard sc ON sc.id = c.id WHERE c.id = :id OR sc.parent = :id ORDER BY sc.parent ASC")
    fun getSavedCards(id: String): LiveData<List<CardLocal>>

    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getCards(mid: String): LiveData<List<Card>>

    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, sc.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, sc.count FROM SavedCard sc LEFT JOIN Card c ON c.id = sc.id WHERE sc.parent = '' ORDER BY c.setTitle, c.numberFormatted")
    fun getAllCards(): DataSource.Factory<Int, Card>

    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, sc.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, sc.count FROM SavedCard sc " +
            "LEFT JOIN Card c ON c.id = sc.id " +
            "LEFT JOIN Type type ON c.id = type.card_id " +
            "LEFT JOIN Subtype sub ON c.id = sub.card_id " +
            "LEFT JOIN Color col ON col.card_id = c.id " +
            "WHERE " +
            "    (sub.subtype IN (:subtypes)  OR (sub.subtype is NULL AND (type.type NOT IN ('Instant', 'Enchantment', 'Sorcery') OR type.type IN (:types)))) " +
            "    AND type.type IN (:types)" +
            "    AND (col.color IN (:colors) OR col.color is null) " +
            "    AND c.rarity IN (:rarities) " +
            "    AND c.`set` IN (:sets) " +
            "    AND sc.parent = '' "+
            "GROUP BY c.id " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getFilteredCards(types: Array<String>, subtypes: Array<String>, colors: Array<String>,
                         rarities: Array<String>, sets: Array<String>): DataSource.Factory<Int, Card>

    @Query("SELECT * FROM WishedCard c WHERE c.id = :id")
    fun getWish(id: String): LiveData<WishedCard>

    @Query("SELECT c.*, sc.count FROM WishedCard wc LEFT JOIN Card c ON c.id = wc.id LEFT JOIN SavedCard sc ON sc.id = wc.id ORDER BY c.setTitle, c.numberFormatted")
    fun getWishedCards(): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM WishedCard wc LEFT JOIN Card c ON c.id = wc.id LEFT JOIN SavedCard sc ON sc.id = wc.id WHERE c.`set` IN (:sets) ORDER BY c.setTitle, c.numberFormatted")
    fun getWishedCardsFiltered(sets: Array<String>): LiveData<List<Card>>

    @Query("SELECT c.*, lc.count, " +
            " MIN(t.type) typeSingle " +
            " FROM Card c, LibraryCard lc, Type t " +
            " WHERE lc.library_id = :library AND lc.card_id = c.id AND t.card_id = c.id " +
            " GROUP BY c.id " +
            " ORDER BY typeSingle, c.setTitle, numberFormatted")
    fun getCardsInLibrary(library: Long): LiveData<List<CardForLibrary>>

    @Query("UPDATE SavedCard SET parent = :parent WHERE id = :id")
    fun updateLink(id: String, parent: String)

    @Query("SELECT * FROM Card c WHERE c.id = :id")
    fun getCardById(id: String): Card

    @Query("SELECT * FROM Card c WHERE c.`set` = :set AND c.number = :number")
    fun getCardBySetAndNumber(set: String, number: String): Card?

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, c.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, c.count, c.number, " +
            "case when substr(c.number, -1) in ('a', 'b') then substr(c.number, 1, length(c.number) - 1) else c.number end num, " +
            "CASE WHEN sc.usd IS NULL THEN 0 ELSE sc.usd END price  " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "LEFT JOIN ScryCard sc ON sc.number = num AND sc.`set` = lower(c.`set`) " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getWatchedCards(): DataSource.Factory<Int, CardWatched>

}