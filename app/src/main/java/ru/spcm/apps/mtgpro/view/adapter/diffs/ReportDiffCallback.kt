package ru.spcm.apps.mtgpro.view.adapter.diffs

import android.support.v7.util.DiffUtil
import ru.spcm.apps.mtgpro.model.dto.ReportCard

class ReportDiffCallback(private val oldList: List<ReportCard>,
                         private val newList: List<ReportCard>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].diff == newList[newItemPosition].diff
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

}