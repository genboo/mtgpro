package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.Ignore

class FilterOption(var title: String = "", var id: String = "") {

    @Ignore
    var selected = false

    @Ignore
    constructor() : this("", "")

}