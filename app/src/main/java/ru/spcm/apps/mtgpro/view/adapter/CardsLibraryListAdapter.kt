package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.view.adapter.diffs.CardsListDiffCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.CardLibraryHolder
import java.util.ArrayList

/**
 * Адаптер для списка карт в колоде
 * Created by gen on 05.07.2018.
 */
class CardsLibraryListAdapter(items: List<CardListItem>?) : RecyclerViewAdapter<CardListItem, CardLibraryHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardLibraryHolder {
        val holder = when (viewType) {
            TYPE_HEADER -> CardLibraryHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_card_header, parent, false))
            else -> CardLibraryHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false))
        }
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: CardLibraryHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).data == null) TYPE_HEADER
        else TYPE_ITEM
    }

    fun setData(data: List<CardForLibrary>) {
        val items = ArrayList<CardListItem>()
        for (i in data.indices) {
            if (i == 0 || data[i - 1].typeSingle != data[i].typeSingle) {
                items.add(CardListItem(data[i].typeSingle, null))
            }
            items.add(CardListItem(null, data[i]))
        }

        val diffs = DiffUtil.calculateDiff(CardsListDiffCallback(getItems(), items), !getItems().isEmpty())
        super.setItems(items)
        diffs.dispatchUpdatesTo(this)
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 2
    }

}