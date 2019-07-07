package ru.spcm.apps.mtgpro.view.adapter

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.diffs.CardsDiffCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.SpoilerHolder

/**
 * Адаптер для списка карт
 * Created by gen on 29.06.2018.
 */

class WishListAdapter(items: List<Card>?) : RecyclerViewAdapter<Card, SpoilerHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpoilerHolder {
        val holder = SpoilerHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_spoiler, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: SpoilerHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun setItems(items: List<Card>) {
        val diffs = DiffUtil.calculateDiff(CardsDiffCallback(getItems(), items), !this.getItems().isEmpty())
        super.setItems(items)
        diffs.dispatchUpdatesTo(this)
    }

}