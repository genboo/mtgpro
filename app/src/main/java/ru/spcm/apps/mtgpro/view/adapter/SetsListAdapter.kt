package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.adapter.holders.SetHolder
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 * Адаптер для списка сетов
 * Created by gen on 22.12.2017.
 */

class SetsListAdapter(items: List<Set>?) : RecyclerViewAdapter<Set, SetHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_set, parent, false)
        val holder = SetHolder(view)
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: SetHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

}
