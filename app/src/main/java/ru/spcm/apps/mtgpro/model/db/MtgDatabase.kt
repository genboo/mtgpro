package ru.spcm.apps.mtgpro.model.db


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.spcm.apps.mtgpro.model.db.converters.DateConverter
import ru.spcm.apps.mtgpro.model.db.dao.*
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * База данных
 * Created by gen on 28.06.2018.
 */
@Database(version = 8, exportSchema = false, entities = [
    Card::class,
    SavedCard::class,
    WishedCard::class,
    WatchedCard::class,
    Set::class,
    Reprint::class,
    Type::class,
    Color::class,
    Supertype::class,
    Subtype::class,
    Library::class,
    LibraryCard::class,
    ScryCard::class,
    PriceHistory::class,
    Cache::class,
    CacheCard::class,
    CacheTime::class
])
@TypeConverters(
        DateConverter::class)
abstract class MtgDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardDao(): CardDao

    abstract fun librariesDao(): LibrariesDao

    abstract fun additionalInfoDao(): AdditionalInfoCardDao

    abstract fun scryCardDao(): ScryCardDao

    abstract fun priceUpdateDao(): PriceUpdateDao

    abstract fun cacheDao(): CacheDao
}
