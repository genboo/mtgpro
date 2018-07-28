package ru.spcm.apps.mtgpro.view.adapter.diffs

import android.support.v7.util.DiffUtil
import ru.spcm.apps.mtgpro.view.adapter.CardListItem

class CardsListDiffCallback(private val oldList: List<CardListItem>,
                            private val newList: List<CardListItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].data?.typeSingle == newList[newItemPosition].data?.typeSingle
                && oldList[oldItemPosition].data?.card?.count == newList[newItemPosition].data?.card?.count
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title ||
                (oldList[oldItemPosition].data?.card?.id == newList[newItemPosition].data?.card?.id)
    }

}