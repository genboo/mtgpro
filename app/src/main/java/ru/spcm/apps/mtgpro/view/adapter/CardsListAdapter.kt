package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.diffs.CardsDiffCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.CardHolder

/**
 * Адаптер для списка карт
 * Created by gen on 29.06.2018.
 */

class CardsListAdapter(items: List<Card>?) : RecyclerViewAdapter<Card, CardHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val holder = CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun setItems(items: List<Card>) {
        val list = getItems().plus(items)
        val diffs = DiffUtil.calculateDiff(CardsDiffCallback(getItems(), list), !this.getItems().isEmpty())
        super.setItems(list)
        diffs.dispatchUpdatesTo(this)
    }

}