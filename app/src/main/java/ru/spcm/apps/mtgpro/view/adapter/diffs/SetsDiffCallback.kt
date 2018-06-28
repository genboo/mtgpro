package ru.spcm.apps.mtgpro.view.adapter.diffs

import android.support.v7.util.DiffUtil
import ru.spcm.apps.mtgpro.model.dto.Set

class SetsDiffCallback(private val oldList: List<Set>,
                       private val newList: List<Set>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].code == newList[newItemPosition].code
    }

}