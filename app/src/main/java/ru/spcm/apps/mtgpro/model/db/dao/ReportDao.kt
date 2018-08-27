package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.model.dto.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Report)

    @Query("SELECT c.id, c.name, c.imageUrl, r.diff, wc.bottom, wc.top, wc.observe, (SELECT price FROM PriceHistory pc WHERE pc.card_id = c.id ORDER BY date DESC) as price FROM Report r LEFT JOIN Card c ON r.card_id = c.id LEFT JOIN WatchedCard wc ON wc.id = c.id")
    fun getReport(): LiveData<List<CardObserved>>

    @Query("DELETE FROM Report")
    fun clear()


}