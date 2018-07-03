package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.SavedCard

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: SavedCard): Long

    @Delete
    fun delete(item: Card)

    @Delete
    fun delete(item: SavedCard)

    @Update
    fun update(item: Card)

    @Query("SELECT c.*, sc.count FROM CacheCard cc LEFT JOIN Card c ON c.id = cc.id  LEFT JOIN SavedCard sc ON sc.id = c.id WHERE cc.cache_key = :cacheKey ORDER BY c.numberFormatted")
    fun getCachedCards(cacheKey: String): LiveData<List<Card>>

    @Transaction
    @Query("SELECT c.*, sc.count FROM Card c LEFT JOIN SavedCard sc ON sc.id = c.id WHERE c.id = :id")
    fun getSavedCards(id: String): LiveData<List<CardLocal>>

    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getCards(mid: String): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM SavedCard sc LEFT JOIN Card c ON c.id = sc.id ORDER BY c.setCode, c.numberFormatted LIMIT 27 OFFSET :offset")
    fun getAllCards(offset: Int): LiveData<List<Card>>
}
