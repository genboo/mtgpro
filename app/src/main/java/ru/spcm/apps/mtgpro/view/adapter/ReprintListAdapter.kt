package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.adapter.holders.ReprintHolder

/**
 * Адаптер для списка репринтов
 * Created by gen on 18.12.2017.
 */

class ReprintListAdapter(items: List<String>?) : RecyclerViewAdapter<String, ReprintHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReprintHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_reprint, parent, false)
        val holder = ReprintHolder(view)
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder

    }

    override fun onBindViewHolder(holder: ReprintHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun setItems(items: List<String>) {
        super.setItems(items.reversed())
        notifyDataSetChanged()
    }

}