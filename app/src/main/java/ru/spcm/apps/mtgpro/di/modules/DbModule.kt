package ru.spcm.apps.mtgpro.di.modules


import android.arch.persistence.room.Room
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ru.devsp.app.mtgcollections.model.db.MtgDatabase

/**
 * Инициализация базы данных
 * Created by gen on 12.09.2017.
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

    companion object {
        const val DB_NAME = "mtg"
    }
}
