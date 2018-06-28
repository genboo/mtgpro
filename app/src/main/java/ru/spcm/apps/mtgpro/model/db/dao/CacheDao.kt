package ru.spcm.apps.mtgpro.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.spcm.apps.mtgpro.model.dto.Cache
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

}