package ru.spcm.apps.mtgpro.model.db.dao


import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.*


/**
 * Цвет, тип, подтипы, суперсипы карты, репринты
 * Created by gen on 02.07.2018.
 */
@Dao
interface AdditionalInfoCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Color)

    @Query("DELETE FROM Color where card_id = :card")
    fun clearColors(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Type)

    @Query("DELETE FROM Type where card_id = :card")
    fun clearTypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Subtype)

    @Query("DELETE FROM Subtype where card_id = :card")
    fun clearSubtypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Supertype)

    @Query("DELETE FROM Supertype where card_id = :card")
    fun clearSupertypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Reprint)

    @Query("DELETE FROM Reprint where card_id = :card")
    fun clearReprints(card: String)

    @Transaction
    fun updateAdditionInfo(card:Card){
        clearColors(card.id)
        card.colors?.forEach {
            insert(Color(card.id, it))
        }

        clearTypes(card.id)
        card.types?.forEach{
            insert(Type(card.id, it))
        }

        clearSupertypes(card.id)
        card.supertypes?.forEach{
            insert(Supertype(card.id, it))
        }

        clearSubtypes(card.id)
        card.subtypes?.forEach{
            insert(Subtype(card.id, it))
        }

        clearReprints(card.id)
        card.printings?.forEach{
            insert(Reprint(card.id, it))
        }
    }

}
