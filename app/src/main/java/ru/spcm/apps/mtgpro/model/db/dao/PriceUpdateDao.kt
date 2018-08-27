package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.model.dto.PriceHistory
import java.util.*

@Dao
interface PriceUpdateDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, c.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, c.count, c.number, " +
            "CASE WHEN SUBSTR(c.number, -1) IN ('a', 'b') THEN SUBSTR(c.number, 1, length(c.number) - 1) ELSE c.number END num, " +
            "CASE WHEN sc.usd IS NULL THEN 0 ELSE sc.usd END price  " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "LEFT JOIN ScryCard sc ON sc.number = num AND sc.`set` = lower(c.`set`) " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getWatchedCardsList(): List<CardWatched>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PriceHistory)

    @Query("SELECT * FROM PriceHistory ph WHERE ph.card_id = :cardId  AND date < :date  ORDER BY ph.date DESC LIMIT 1 ")
    fun getLastPrice(cardId: String, date: Date): PriceHistory?


}