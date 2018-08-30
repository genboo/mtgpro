package ru.spcm.apps.mtgpro.services

import android.arch.lifecycle.MutableLiveData
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.db.dao.LibrariesDao
import ru.spcm.apps.mtgpro.model.db.dao.ScryCardDao
import ru.spcm.apps.mtgpro.model.db.dao.SettingsDao
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger
import java.io.IOException
import javax.inject.Inject

class LibrariesCardsPriceUpdater @Inject
constructor(private val appExecutors: AppExecutors,
            private val librariesDao: LibrariesDao,
            private val scryCardDao: ScryCardDao,
            private val settingsDao: SettingsDao,
            private val scryCardApi: ScryCardApi) {

    val result = MutableLiveData<UpdateResult>()

    fun update() {
        appExecutors.networkIO().execute {
            val setting = settingsDao.getSetting(Setting.Type.UPDATE_LIBRARY_CARDS_PRICE)
            if (setting != null && setting.value.toBoolean()) {
                val cards = librariesDao.getCardsInAllLibraries()
                var updateCounter = 0
                cards.forEach { item ->
                    if (item.number != null) {
                        val number = item.number?.replace("a", "")?.replace("b", "")
                        var price = getPrice(item.set, number ?: "")
                        for (i in 0..9) {
                            if (price != null) {
                                break
                            }
                            Thread.sleep(3000)
                            price = getPrice(item.set, number ?: "")
                        }
                        if (price != null) {
                            if (price.eur == null) {
                                price.eur = ""
                            }
                            scryCardDao.insert(price)
                        }
                        updateCounter++
                    }

                    if (updateCounter % 10 == 0 && updateCounter < cards.size) {
                        appExecutors.mainThread().execute {
                            val data = UpdateResult()
                            data.allCount = cards.size
                            data.currentCard = updateCounter
                            result.postValue(data)
                        }
                    }
                    Thread.sleep(500)
                }

                appExecutors.mainThread().execute {
                    val data = UpdateResult()
                    data.allCount = cards.size
                    data.updatedCount = updateCounter
                    result.postValue(data)
                }
            }
        }
    }

    private fun getPrice(set: String, number: String): ScryCard? {
        var price: ScryCard? = null
        try {
            price = scryCardApi.getCardByNumberCall(set.toLowerCase(), number).execute().body()
        } catch (ex: IOException) {
            Logger.e(ex)
        }
        return price
    }
}