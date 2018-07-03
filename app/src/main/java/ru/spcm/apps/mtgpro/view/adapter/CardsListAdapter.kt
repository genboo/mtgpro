package ru.spcm.apps.mtgpro.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.holders.CardHolder

/**
 * Адаптер для списка карт
 * Created by gen on 29.06.2018.
 */

class CardsListAdapter : PagedListAdapter<Card, CardHolder>(DIFF_CALLBACK) {

    var listener: (Card) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val holder = CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false))
        holder.setListener(View.OnClickListener {
            val card = getItem(holder.adapterPosition)
            if (card != null) {
                listener(card)
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

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.set == newItem.set
                        && oldItem.count == newItem.count
            }

            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}