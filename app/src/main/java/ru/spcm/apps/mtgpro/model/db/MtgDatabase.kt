package ru.spcm.apps.mtgpro.model.db


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.spcm.apps.mtgpro.model.db.converters.DateConverter
import ru.spcm.apps.mtgpro.model.db.converters.SettingTypeConverter
import ru.spcm.apps.mtgpro.model.db.dao.*
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * База данных
 * Created by gen on 28.06.2018.
 */
@Database(version = 10, exportSchema = false, entities = [
    Card::class,
    SavedCard::class,
    WishedCard::class,
    WatchedCard::class,
    Set::class,
    Reprint::class,
    Setting::class,
    Type::class,
    Color::class,
    Supertype::class,
    Subtype::class,
    Library::class,
    LibraryCard::class,
    ScryCard::class,
    PriceHistory::class,
    Report::class,
    Cache::class,
    CacheCard::class,
    CacheTime::class
])
@TypeConverters(
        SettingTypeConverter::class,
        DateConverter::class)
abstract class MtgDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardDao(): CardDao

    abstract fun librariesDao(): LibrariesDao

    abstract fun additionalInfoDao(): AdditionalInfoCardDao

    abstract fun scryCardDao(): ScryCardDao

    abstract fun priceUpdateDao(): PriceUpdateDao

    abstract fun reportDao(): ReportDao

    abstract fun settingsDao(): SettingsDao

    abstract fun cacheDao(): CacheDao
}
