package ru.spcm.apps.mtgpro.view.adapter

import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.model.dto.LibraryColorState
import ru.spcm.apps.mtgpro.model.dto.LibraryManaState


class CardListItem(val title: String?, val data: CardForLibrary?) {
    var colorState: List<LibraryColorState>? = null
    var manaState: List<LibraryManaState>? = null
}