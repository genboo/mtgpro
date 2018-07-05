package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.view.adapter.holders.CardLibraryHolder
import java.util.ArrayList

/**
 * Адаптер для списка карт
 * Created by gen on 05.07.2018.
 */
class CardsLibraryListAdapter(items: List<CardListItem>?) : RecyclerViewAdapter<CardListItem, CardLibraryHolder>(items) {

    fun setCards(cards: List<CardForLibrary>) {
        val items = ArrayList<CardListItem>()
        for (i in cards.indices) {
            if (i == 0 || cards[i - 1].typeSingle != cards[i].typeSingle) {
                items.add(CardListItem(cards[i].typeSingle, null))
            }
            items.add(CardListItem(null, cards[i]))
        }
        super.setItems(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardLibraryHolder = when (viewType) {
        TYPE_HEADER -> CardLibraryHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_card_header, parent, false))
        else -> CardLibraryHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false))
    }

    override fun onBindViewHolder(holder: CardLibraryHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).data == null) TYPE_HEADER else TYPE_ITEM
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 2
    }

}