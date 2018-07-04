package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_library.view.*
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo

class LibraryHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: LibraryInfo) = with(itemView) {
        libraryName.text = item.name
        libraryCardsCount.text = String.format("Карт в колоде: %s", item.cardsCount)
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}