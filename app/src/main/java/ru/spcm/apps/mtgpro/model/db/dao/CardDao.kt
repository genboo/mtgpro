package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.SavedCard
import ru.spcm.apps.mtgpro.model.dto.WishedCard

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

    @Query("SELECT c.*, sc.count FROM CacheCard cc LEFT JOIN Card c ON c.id = cc.id LEFT JOIN SavedCard sc ON sc.id = c.id WHERE cc.cache_key = :cacheKey ORDER BY c.numberFormatted")
    fun getCachedCards(cacheKey: String): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM Card c LEFT JOIN SavedCard sc ON sc.id = c.id WHERE c.id = :id")
    fun getSavedCards(id: String): LiveData<List<CardLocal>>

    @Query("SELECT c.* FROM Card c WHERE c.multiverseId = :mid")
    fun getCards(mid: String): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM SavedCard sc LEFT JOIN Card c ON c.id = sc.id ORDER BY c.setCode, c.numberFormatted LIMIT :limit OFFSET :offset")
    fun getAllCards(offset: Int, limit: Int): LiveData<List<Card>>

    @Query("SELECT c.*, sc.count FROM SavedCard sc LEFT JOIN Card c ON c.id = sc.id ORDER BY c.setCode, c.numberFormatted")
    fun getAllCards(): DataSource.Factory<Int, Card>

    @Query("SELECT * FROM WishedCard c WHERE c.id = :id")
    fun getWish(id: String): LiveData<WishedCard>

    @Query("SELECT c.*, sc.count FROM WishedCard wc LEFT JOIN Card c ON c.id = wc.id  LEFT JOIN SavedCard sc ON sc.id = wc.id ORDER BY c.setCode, c.numberFormatted")
    fun getWishedCards(): LiveData<List<Card>>
}
