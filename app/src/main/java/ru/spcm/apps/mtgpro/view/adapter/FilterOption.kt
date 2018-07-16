package ru.spcm.apps.mtgpro.view.adapter

import android.arch.persistence.room.Ignore

class FilterOption(var title: String = "", var id: String = ""){

    @Ignore
    constructor() :this("", "")
}