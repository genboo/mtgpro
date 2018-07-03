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
    fun insertColors(item: List<Color>)

    @Query("DELETE FROM Color where card_id = :card")
    fun clearColors(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(item: List<Type>)

    @Query("DELETE FROM Type where card_id = :card")
    fun clearTypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubtypes(item: List<Subtype>)

    @Query("DELETE FROM Subtype where card_id = :card")
    fun clearSubtypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupertypes(item: List<Supertype>)

    @Query("DELETE FROM Supertype where card_id = :card")
    fun clearSupertypes(card: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReprints(item: List<Reprint>)

    @Query("DELETE FROM Reprint where card_id = :card")
    fun clearReprints(card: String)

    @Transaction
    fun updateAdditionInfo(card: Card) {
        clearColors(card.id)
        val colors = ArrayList<Color>()
        card.colors?.forEach {
            colors.add(Color(card.id, it))
        }
        insertColors(colors)

        clearTypes(card.id)
        val types = ArrayList<Type>()
        card.types?.forEach {
            types.add(Type(card.id, it))
        }
        insertTypes(types)

        clearSupertypes(card.id)
        val supertypes = ArrayList<Supertype>()
        card.supertypes?.forEach {
            supertypes.add(Supertype(card.id, it))
        }
        insertSupertypes(supertypes)

        clearSubtypes(card.id)
        val subtypes = ArrayList<Subtype>()
        card.subtypes?.forEach {
            subtypes.add(Subtype(card.id, it))
        }
        insertSubtypes(subtypes)

        clearReprints(card.id)
        val printings = ArrayList<Reprint>()
        card.printings?.forEach {
            printings.add(Reprint(card.id, it))
        }
        insertReprints(printings)

    }

}
