package ru.spcm.apps.mtgpro.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.spcm.apps.mtgpro.model.dto.Cache
import ru.spcm.apps.mtgpro.model.dto.CacheCard
import ru.spcm.apps.mtgpro.model.dto.CacheTime

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache)

    @Query("SELECT * FROM Cache WHERE cache_key = :key")
    fun getCache(key: String): LiveData<Cache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: CacheTime)

    @Query("SELECT * FROM CacheTime WHERE cache_type = :type")
    fun getCacheType(type: String): CacheTime

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: CacheCard)

}