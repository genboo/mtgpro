package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.adapter.holders.SetHolder
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.view.adapter.diffs.SetsDiffCallback

/**
 * Адаптер для списка сетов
 * Created by gen on 28.06.2018.
 */

class SetsListAdapter(items: List<Set>?) : RecyclerViewAdapter<Set, SetHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetHolder {
        val holder = SetHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_set, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: SetHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun setItems(items: List<Set>) {
        val diffs = DiffUtil.calculateDiff(SetsDiffCallback(getItems(), items), !getItems().isEmpty())
        super.setItems(items)
        diffs.dispatchUpdatesTo(this)
    }

}
