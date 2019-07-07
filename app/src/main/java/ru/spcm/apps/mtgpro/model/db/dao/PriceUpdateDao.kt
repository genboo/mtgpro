package ru.spcm.apps.mtgpro.model.db.dao

import androidx.room.*
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.model.dto.PriceHistory
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import java.util.*

@Dao
interface PriceUpdateDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.numberFormatted, c.nameOrigin, c.setTitle, c.parent, c.imageUrl, c.name, c.rarity, c.multiverseId, c.number, c.`set`, c.type, c.cmc, c.text, c.flavor, c.manaCost, c.rulesText, c.count, c.number, " +
            "CASE WHEN sc.usd IS NULL THEN 0 ELSE sc.usd END price  " +
            "FROM WatchedCard wc " +
            "LEFT JOIN Card c ON c.id = wc.id " +
            "LEFT JOIN ScryCard sc ON sc.number = c.number AND sc.`set` = lower(c.`set`) " +
            "WHERE c.id IS NOT NULL " +
            "ORDER BY c.setTitle, c.numberFormatted")
    fun getWatchedCardsList(): List<CardWatched>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PriceHistory)

    @Query("SELECT * FROM PriceHistory ph WHERE ph.card_id = :cardId  AND date < :date  ORDER BY ph.date DESC LIMIT 1 ")
    fun getLastPrice(cardId: String, date: Date): PriceHistory?

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT sc.* " +
            "FROM Card c " +
            "LEFT JOIN ScryCard sc ON sc.number = c.number AND sc.`set` = lower(c.`set`) " +
            "WHERE c.id = :id")
    fun getPrice(id: String): ScryCard

    @Query("DELETE FROM PriceHistory WHERE id = 14956")
    fun deleteBugedPrice()


}