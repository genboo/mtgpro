package ru.spcm.apps.mtgpro.di.modules


import android.arch.persistence.room.Room
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ru.spcm.apps.mtgpro.model.db.MtgDatabase
import ru.spcm.apps.mtgpro.model.db.dao.CacheDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.db.dao.SetsDao

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
                .build()
    }

    @Provides
    @Singleton
    internal fun provideCardDao(db: MtgDatabase): CardDao {
        return db.cardDao()
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

    companion object {
        const val DB_NAME = "mtg"
    }
}
