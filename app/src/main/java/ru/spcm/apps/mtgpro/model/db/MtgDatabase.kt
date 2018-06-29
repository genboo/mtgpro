package ru.spcm.apps.mtgpro.model.db


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * База данных
 * Created by gen on 28.06.2018.
 */
@Database(version = 1, exportSchema = false, entities = [
    Card::class,
    Set::class,
    Cache::class,
    CacheCard::class,
    CacheTime::class
])
abstract class MtgDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardDao(): CardDao

    abstract fun cacheDao(): CacheDao
}
