package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.spcm.apps.mtgpro.model.dto.Report
import ru.spcm.apps.mtgpro.model.dto.ReportCard

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Report)

    @Query("SELECT c.id, c.name, c.imageUrl, r.diff FROM Report r LEFT JOIN Card c ON r.card_id = c.id")
    fun getReport(): LiveData<List<ReportCard>>

    @Query("DELETE FROM Report")
    fun clear()


}