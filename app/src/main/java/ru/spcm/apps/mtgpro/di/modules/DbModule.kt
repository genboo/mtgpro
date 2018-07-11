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
                        MIGRATION_1_2
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
    internal fun provideAdditionalInfoDao(db: MtgDatabase): AdditionalInfoCardDao{
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


    companion object {
        const val DB_NAME = "mtg"

        /**
         * Список миграций
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Оригинальное название карты
                database.execSQL("ALTER TABLE Card " + " ADD COLUMN nameOrigin TEXT")
            }
        }
    }
}
