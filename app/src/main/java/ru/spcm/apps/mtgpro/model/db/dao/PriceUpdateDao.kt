package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.model.dto.PriceHistory
import java.util.*

@Dao
interface PriceUpdateDao {


    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, c.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, c.count, sc.usd price " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "LEFT JOIN ScryCard sc ON sc.number = c.number AND sc.`set` = lower(c.`set`) " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getWatchedCardsList(): List<CardWatched>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PriceHistory)

    @Query("SELECT * FROM PriceHistory ph WHERE ph.card_id = :cardId  AND date < :date  ORDER BY ph.date DESC LIMIT 1 ")
    fun getLastPrice(cardId: String, date: Date): PriceHistory?


}