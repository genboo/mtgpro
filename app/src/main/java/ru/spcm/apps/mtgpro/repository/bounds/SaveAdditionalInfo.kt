package ru.spcm.apps.mtgpro.repository.bounds

import android.support.annotation.WorkerThread
import ru.spcm.apps.mtgpro.model.db.dao.AdditionalInfoCardDao
import ru.spcm.apps.mtgpro.model.dto.*

object SaveAdditionalInfo{

    @WorkerThread
    fun save(additionalInfoCardDao: AdditionalInfoCardDao, card:Card) {
        additionalInfoCardDao.clearColors(card.id)
        card.colors?.forEach {
            additionalInfoCardDao.insert(Color(card.id, it))
        }

        additionalInfoCardDao.clearTypes(card.id)
        card.types?.forEach{
            additionalInfoCardDao.insert(Type(card.id, it))
        }

        additionalInfoCardDao.clearSupertypes(card.id)
        card.supertypes?.forEach{
            additionalInfoCardDao.insert(Supertype(card.id, it))
        }

        additionalInfoCardDao.clearSubtypes(card.id)
        card.subtypes?.forEach{
            additionalInfoCardDao.insert(Subtype(card.id, it))
        }

        additionalInfoCardDao.clearReprints(card.id)
        card.printings?.forEach{
            additionalInfoCardDao.insert(Reprint(card.id, it))
        }
    }
}