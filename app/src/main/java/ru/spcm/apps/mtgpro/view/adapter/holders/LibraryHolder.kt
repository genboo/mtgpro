package ru.spcm.apps.mtgpro.view.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_library.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.tools.format

class LibraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: LibraryInfo) = with(itemView) {
        libraryName.text = item.name
        libraryCardsCount.text = libraryCardsCount.context.getString(R.string.library_card_count, item.cardsCount)
        libraryPrice.text = libraryCardsCount.context.getString(R.string.library_price_rub, item.price?.format() ?: "0")
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}