package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal

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

    @Query("SELECT c.* FROM CacheCard cc LEFT JOIN Card c ON c.id = cc.id WHERE cc.cache_key = :cacheKey ORDER BY c.numberFormatted")
    fun getCachedCards(cacheKey: String): LiveData<List<Card>>

    @Transaction
    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getSavedCards(mid: String): LiveData<List<CardLocal>>

    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getCards(mid: String): LiveData<List<Card>>

}
