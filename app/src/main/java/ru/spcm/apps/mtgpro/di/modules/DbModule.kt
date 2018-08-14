package ru.spcm.apps.mtgpro.di.modules


import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ru.spcm.apps.mtgpro.model.db.MtgDatabase
import ru.spcm.apps.mtgpro.model.db.dao.*

/**
 * Инициализация базы данных
 * Created by gen on 28.06.2018.
 */
@Suppress("unused")
@Module
class DbModule {

    /**
     * Базовый провайдер базы данных
     *
     * @param context Контекст
     * @return База данных
     */
    @Provides
    @Singleton
    internal fun provideDatabase(context: Context): MtgDatabase {
        return Room
                .databaseBuilder(context, MtgDatabase::class.java, DB_NAME)
                .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_5_6,
                        MIGRATION_6_7
                )
                .build()
    }

    @Provides
    @Singleton
    internal fun provideCardDao(db: MtgDatabase): CardDao {
        return db.cardDao()
    }

    @Provides
    @Singleton
    internal fun provideAdditionalInfoDao(db: MtgDatabase): AdditionalInfoCardDao {
        return db.additionalInfoDao()
    }

    @Provides
    @Singleton
    internal fun provideSetsDao(db: MtgDatabase): SetsDao {
        return db.setsDao()
    }

    @Provides
    @Singleton
    internal fun provideCacheDao(db: MtgDatabase): CacheDao {
        return db.cacheDao()
    }

    @Provides
    @Singleton
    internal fun provideLibrariesDao(db: MtgDatabase): LibrariesDao {
        return db.librariesDao()
    }

    @Provides
    @Singleton
    internal fun provideScryCardDao(db: MtgDatabase): ScryCardDao {
        return db.scryCardDao()
    }


    companion object {
        const val DB_NAME = "mtg"

        /**
         * Список миграций
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Оригинальное название карты
                database.execSQL("ALTER TABLE Card ADD COLUMN nameOrigin TEXT")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Пересоздание таблицы закешированных карт
                database.execSQL("CREATE TABLE temp_table AS SELECT * FROM CacheCard")
                database.execSQL("DROP TABLE CacheCard")

                database.execSQL("CREATE TABLE CacheCard (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, card_id TEXT NOT NULL, cache_key TEXT NOT NULL)")
                database.execSQL("INSERT INTO CacheCard (card_id, cache_key) SELECT id, cache_key FROM temp_table")
                database.execSQL("DROP TABLE temp_table")
                database.execSQL("CREATE INDEX index_CacheCard_cache_key ON CacheCard (cache_key)")
                database.execSQL("CREATE UNIQUE INDEX index_CacheCard_id_cache_key ON CacheCard (card_id, cache_key)")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Добавление индекса для поля
                database.execSQL("CREATE INDEX index_Card_set ON Card (\"set\")")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Добавление таблицы с ценами
                database.execSQL("CREATE TABLE ScryCard (id TEXT NOT NULL PRIMARY KEY, usd TEXT NOT NULL, eur TEXT NOT NULL, [set] TEXT NOT NULL, number TEXT NOT NULL)")
                database.execSQL("CREATE INDEX index_ScryCard_set_number ON ScryCard (\"set\", number)")

            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Добавление таблицы для слежения
                database.execSQL("CREATE TABLE WatchedCard ( id TEXT NOT NULL, PRIMARY KEY ( id ))")
            }
        }

        private val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Добавление таблицы для слежения
                database.execSQL("CREATE TABLE PriceHistory (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date TEXT NOT NULL, card_id TEXT NOT NULL, price TEXT NOT NULL)")
                database.execSQL("CREATE INDEX index_PriceHistory_date ON PriceHistory (date)")
                database.execSQL("CREATE UNIQUE INDEX index_PriceHistory_card_id_date ON PriceHistory (card_id,date)")
            }
        }
    }
}
