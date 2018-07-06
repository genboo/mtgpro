package ru.spcm.apps.mtgpro.view.adapter

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.diffs.CardsDiffItemCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.CardHolder

/**
 * Адаптер для списка карт
 * Created by gen on 29.06.2018.
 */

class CardsListAdapter : PagedListAdapter<Card, CardHolder>(CardsDiffItemCallback) {

    var listener: (Card) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val holder = CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false))
        holder.setListener(View.OnClickListener {
            val card = getItem(holder.adapterPosition)
            if (card != null) {
                parent.postDelayed({ listener(card) }, 100)
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val card = getItem(holder.adapterPosition)
        if (card != null) {
            holder.bind(card)
        }
    }
}