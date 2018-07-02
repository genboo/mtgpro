package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Reprint
import ru.spcm.apps.mtgpro.view.adapter.holders.ReprintHolder

/**
 * Адаптер для списка репринтов
 * Created by gen on 18.12.2017.
 */

class ReprintListAdapter(items: List<Reprint>?) : RecyclerViewAdapter<Reprint, ReprintHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReprintHolder {
        val view = when (viewType) {
            TYPE_OFFSET -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_offset, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_reprint, parent, false)
        }
        val holder = ReprintHolder(view)
        if(viewType == TYPE_MAIN) {
            holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition - 1) })
        }
        return holder

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == itemCount - 1)
            TYPE_OFFSET
        else
            TYPE_MAIN
    }

    override fun onBindViewHolder(holder: ReprintHolder, position: Int) {
        if (getItemViewType(holder.adapterPosition) == TYPE_MAIN) {
            holder.bind(getItem(position - 1).reprint)
        }
    }

    override fun setItems(items: List<Reprint>) {
        super.setItems(items.reversed())
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_OFFSET = 1
        private const val TYPE_MAIN = 2
    }

}