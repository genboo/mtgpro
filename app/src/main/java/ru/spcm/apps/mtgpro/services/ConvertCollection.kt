package ru.spcm.apps.mtgpro.services

import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.db.dao.CardDao
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger
import javax.inject.Inject

class ConvertCollection @Inject
constructor(private val appExecutors: AppExecutors,
            private val cardApi: CardApi,
            private val cardDao: CardDao) {

    val result = MutableLiveData<Int>()

    fun convert() {
        appExecutors.diskIO().execute {
            cardDao.clearCached()
            cardDao.clearCache()
            for (i in sets.indices) {
                updateSetCards(sets[i])
                result.postValue(i + 1)
            }
            result.postValue(35000)
        }
    }


    private fun updateSetCards(set: String) {
        var page = 1
        do {
            val list = getCards(set, page)

            for (card in list) {
                card.prepare()
                cardDao.insert(card)
                val name = card.nameOrigin ?: continue
                val number = card.numberFormatted
                val oldCard = cardDao.getCardSaved(card.set, number, name) ?: continue

                cardDao.updateSaved(card.id, oldCard.id)
                cardDao.updateParent(card.id, oldCard.id)
                cardDao.updateColors(card.id, oldCard.id)
                cardDao.updateSubtypes(card.id, oldCard.id)
                cardDao.updateSupertypes(card.id, oldCard.id)
                cardDao.updateTypes(card.id, oldCard.id)
                cardDao.updateTypes(card.id, oldCard.id)
                cardDao.updateReprints(card.id, oldCard.id)
                cardDao.updateWish(card.id, oldCard.id)
                cardDao.updateWatched(card.id, oldCard.id)
                cardDao.updateLibrary(card.id, oldCard.id)
                cardDao.updateReport(card.id, oldCard.id)

                cardDao.clearOld(card.id, card.set, number, name)
            }
            Logger.e("page $page")
            page++
        } while (list.isNotEmpty())


    }


    private fun getCards(set: String, page: Int): List<Card> {
        val list = cardApi.getCardsBySetCall(set, page, PAGE_SIZE).execute().body()
        return list ?: arrayListOf()
    }

    companion object {
        const val PAGE_SIZE = 30
        val sets = arrayOf(
                "RNA", "GRN", "M19", "DOM", "RIX", "XLN",
                "HOU", "MP2", "AKH", "W17", "AER", "KLD", "MPS",
                "EMN", "SOI", "OGW", "BFZ", "EXP", "ORI", "DTK", "FRF", "KTK", "M15"
        )
    }

}