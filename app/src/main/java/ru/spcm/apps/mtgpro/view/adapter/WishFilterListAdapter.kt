package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.SetName
import ru.spcm.apps.mtgpro.view.adapter.diffs.SetNamesDiffCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.WishFilterHolder

/**
 * Адаптер для фильтра в списке желаний
 * Created by gen on 07.08.2018.
 */

class WishFilterListAdapter(items: List<SetName>?) : RecyclerViewAdapter<SetName, WishFilterHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishFilterHolder {
        val holder = WishFilterHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_wish_filter, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: WishFilterHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun setItems(items: List<SetName>) {
        val diffs = DiffUtil.calculateDiff(SetNamesDiffCallback(getItems(), items), !this.getItems().isEmpty())
        super.setItems(items)
        diffs.dispatchUpdatesTo(this)
    }

}