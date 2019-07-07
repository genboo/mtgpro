package ru.spcm.apps.mtgpro.view.adapter

import androidx.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.view.adapter.diffs.CardsWatchedDiffItemCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.CardWatchedHolder

/**
 * Адаптер для списка карт
 * Created by gen on 14.08.2018.
 */

class CardsWatchedListAdapter : PagedListAdapter<CardWatched, CardWatchedHolder>(CardsWatchedDiffItemCallback) {

    var listener: (CardWatched) -> Unit = {}
    var deleteListener: (CardWatched) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardWatchedHolder {
        val holder = CardWatchedHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card_watched, parent, false))
        holder.setListener(View.OnClickListener {
            val card = getItem(holder.adapterPosition)
            if (card != null) {
                parent.postDelayed({ listener(card) }, 100)
            }
        })

        holder.setDeleteListener(View.OnClickListener {
            val card = getItem(holder.adapterPosition)
            if (card != null) {
                deleteListener(card)
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: CardWatchedHolder, position: Int) {
        val card = getItem(holder.adapterPosition)
        if (card != null) {
            holder.bind(card)
        }
    }
}