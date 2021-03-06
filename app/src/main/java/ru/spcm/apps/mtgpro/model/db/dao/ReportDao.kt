package ru.spcm.apps.mtgpro.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.model.dto.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Report)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT c.id, c.imageUrl, r.diff * :valute AS diff, wc.bottom * :valute AS bottom, wc.top * :valute AS top, wc.observe, (SELECT price FROM PriceHistory pc WHERE pc.card_id = c.id ORDER BY date DESC) * :valute AS price FROM Report r LEFT JOIN Card c ON r.card_id = c.id LEFT JOIN WatchedCard wc ON wc.id = c.id WHERE c.id IS NOT NULL")
    fun getReport(valute: Float): LiveData<List<CardObserved>>

    @Query("DELETE FROM Report")
    fun clear()

}