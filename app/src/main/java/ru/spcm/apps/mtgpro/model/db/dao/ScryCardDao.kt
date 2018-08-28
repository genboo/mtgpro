package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.*
import java.util.*

/**
 * Сохраненные карты
 * Created by gen on 13.08.2018.
 */
@Dao
interface ScryCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ScryCard): Long

    @Delete
    fun delete(item: ScryCard)

    @Query("SELECT sc.number, sc.`set`, sc.usd AS usd, sc.eur AS eur, sc.id FROM ScryCard sc WHERE sc.number=:number AND sc.`set`=:set")
    fun getPrices(set: String, number: String): LiveData<ScryCard>

    @Query("SELECT sc.number, sc.`set`, sc.usd * :valute AS usd, sc.eur * :valute AS eur, sc.id FROM ScryCard sc WHERE sc.number=:number AND sc.`set`=:set")
    fun getConvertedPrices(set: String, number: String, valute: Float): LiveData<ScryCard>

    @Query("SELECT ph.date, ph.price * :valute count FROM PriceHistory ph WHERE ph.card_id=:id AND ph.date BETWEEN :from AND :to")
    fun getData(id: String, valute: Float, from: Date, to: Date): LiveData<List<GraphDot>>

}