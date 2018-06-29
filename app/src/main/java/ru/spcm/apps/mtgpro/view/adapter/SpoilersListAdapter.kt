package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.holders.SpoilerHolder
import java.util.HashMap


/**
 * Адаптер для списка карт
 * Created by gen on 29.06.2018.
 */

class SpoilersListAdapter(items: List<Card>?) : RecyclerViewAdapter<Card, SpoilerHolder>(items) {

    private var loading = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpoilerHolder {
        val view = when (viewType) {
            TYPE_LOADING -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.list_item_spoiler, parent, false)
        }
        val holder = SpoilerHolder(view)
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: SpoilerHolder, position: Int) {
        if (getItemViewType(position) == TYPE_MAIN) {
            holder.bind(getItem(holder.adapterPosition))
        } else {
            holder.switchLoading(loading)
        }

    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < getSize()) TYPE_MAIN else TYPE_LOADING
    }

    companion object {
        const val TYPE_MAIN = 1
        const val TYPE_LOADING = 2
    }

}