package ru.spcm.apps.mtgpro.model.dto

class FilterItem {
    var title: String = ""
    var id: String = ""

    var options: List<FilterOption> = arrayListOf()

    companion object {
        const val BLOCK_COLOR = "color"
        const val BLOCK_RARITY = "rarity"
        const val BLOCK_TYPE = "type"
        const val BLOCK_SUBTYPE = "subtype"
        const val BLOCK_SET = "set"

    }
}