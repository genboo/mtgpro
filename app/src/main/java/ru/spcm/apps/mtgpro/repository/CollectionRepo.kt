package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.view.adapter.FilterItem
import ru.spcm.apps.mtgpro.view.adapter.FilterOption
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
                    FilterOption("Mythic Rare", "Mythic Rare"),
                    FilterOption("Special", "Special")
            )
        }

    fun getAllCards(): DataSource.Factory<Int, Card> {
        return cardDao.getAllCards()
    }

    fun getFilters(): LiveData<List<FilterItem>> {
        val filter = MutableLiveData<List<FilterItem>>()

        appExecutors.diskIO().execute {
            val colorsItem = FilterItem()
            colorsItem.title = "Цвет"
            colorsItem.options = additionalInfoCardDao.getColors()

            val rarityItem = FilterItem()
            rarityItem.title = "Редкость"
            rarityItem.options = rarities

            val typesItem = FilterItem()
            typesItem.title = "Тип"
            typesItem.options = additionalInfoCardDao.getTypes()

            val setsItem = FilterItem()
            setsItem.title = "Набор"
            setsItem.options = additionalInfoCardDao.getSets()

            val subtypesItem = FilterItem()
            subtypesItem.title = "Подтип"
            subtypesItem.options = additionalInfoCardDao.getSubtypes()

            filter.postValue(arrayListOf(colorsItem, rarityItem, typesItem, subtypesItem, setsItem))
        }

        return filter
    }

}