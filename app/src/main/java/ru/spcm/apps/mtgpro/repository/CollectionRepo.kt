package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.CardCollection
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.model.dto.FilterItem
import ru.spcm.apps.mtgpro.model.dto.FilterOption
import javax.inject.Inject

class CollectionRepo @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardDao: CardDao,
            private val additionalInfoCardDao: AdditionalInfoCardDao) {

    private val rarities: List<FilterOption>
        get() {
            return arrayListOf(
                    FilterOption("Basic Land", "Basic Land"),
                    FilterOption("Common", "Common"),
                    FilterOption("Uncommon", "Uncommon"),
                    FilterOption("Rare", "Rare"),
                    FilterOption("Mythic", "Mythic"),
                    FilterOption("Special", "Special")
            )
        }

    fun getAllCards(valute: Float): DataSource.Factory<Int, CardCollection> {
        return cardDao.getAllCards(valute)
    }

    fun getFilteredCards(valute: Float, types: Array<String>, subtypes: Array<String>, colors: Array<String>,
                         rarities: Array<String>, sets: Array<String>): DataSource.Factory<Int, CardCollection> {
        return cardDao.getFilteredCards(valute, types, subtypes, colors, rarities, sets)
    }

    fun getFilters(): LiveData<List<FilterItem>> {
        val filter = MutableLiveData<List<FilterItem>>()

        appExecutors.diskIO().execute {
            val colorsItem = FilterItem()
            colorsItem.title = "Цвет"
            colorsItem.id = FilterItem.BLOCK_COLOR
            colorsItem.options = additionalInfoCardDao.getColors()

            val rarityItem = FilterItem()
            rarityItem.title = "Редкость"
            rarityItem.id = FilterItem.BLOCK_RARITY
            rarityItem.options = rarities

            val typesItem = FilterItem()
            typesItem.title = "Тип"
            typesItem.id = FilterItem.BLOCK_TYPE
            typesItem.options = additionalInfoCardDao.getTypes()

            val setsItem = FilterItem()
            setsItem.title = "Набор"
            setsItem.id = FilterItem.BLOCK_SET
            setsItem.options = additionalInfoCardDao.getSets()

            val subtypesItem = FilterItem()
            subtypesItem.title = "Подтип"
            subtypesItem.id = FilterItem.BLOCK_SUBTYPE
            subtypesItem.options = additionalInfoCardDao.getSubtypes()

            filter.postValue(arrayListOf(colorsItem, rarityItem, typesItem, subtypesItem, setsItem))
        }

        return filter
    }

}