package ru.spcm.apps.mtgpro.tools

import ru.spcm.apps.mtgpro.model.dto.LibraryColorState

fun LibraryColorState.getColor(): String {

    return when(color){
        "Black" -> "{B}"
        "Blue" -> "{U}"
        "Red" -> "{R}"
        "White" -> "{W}"
        "Green" -> "{G}"
        else -> "{C}"
    }

}