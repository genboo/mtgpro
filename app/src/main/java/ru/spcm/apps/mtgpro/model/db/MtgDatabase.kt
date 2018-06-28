package ru.devsp.app.mtgcollections.model.db


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * База данных
 * Created by gen on 28.06.2018.
 */
@Database(version = 1, exportSchema = false, entities = [
    Card::class,
    Set::class
])
abstract class MtgDatabase : RoomDatabase() {

}
