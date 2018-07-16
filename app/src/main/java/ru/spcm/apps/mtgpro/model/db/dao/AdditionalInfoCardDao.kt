package ru.spcm.apps.mtgpro.model.db.dao


import android.arch.persistence.room.*
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.view.adapter.FilterOption


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

    @Query("SELECT col.color as title, col.color as id FROM Color col GROUP BY col.color ORDER BY col.color")
    fun getColors(): List<FilterOption>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(item: List<Type>)

    @Query("DELETE FROM Type where card_id = :card")
    fun clearTypes(card: String)

    @Query("SELECT type.type as title, type.type as id FROM Type type, SavedCard sc WHERE sc.id = type.card_id GROUP BY type.type ORDER BY type.type")
    fun getTypes(): List<FilterOption>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubtypes(item: List<Subtype>)

    @Query("DELETE FROM Subtype where card_id = :card")
    fun clearSubtypes(card: String)

    @Query("SELECT sub.subtype as title, sub.subtype as id FROM Subtype sub, SavedCard sc WHERE sc.id = sub.card_id GROUP BY sub.subtype ORDER BY sub.subtype")
    fun getSubtypes(): List<FilterOption>

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

    @Query("SELECT c.`setTitle` as title, c.`set` as id FROM Card c, SavedCard sc WHERE sc.id = c.id GROUP BY c.`set` ORDER BY c.setTitle")
    fun getSets(): List<FilterOption>

}
