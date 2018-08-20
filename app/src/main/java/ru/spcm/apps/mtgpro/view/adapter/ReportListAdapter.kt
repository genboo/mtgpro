package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.ReportCard
import ru.spcm.apps.mtgpro.view.adapter.diffs.ReportDiffCallback
import ru.spcm.apps.mtgpro.view.adapter.holders.ReportHolder

/**
 * Адаптер для отчета
 * Created by gen on 20.08.2018.
 */

class ReportListAdapter(items: List<ReportCard>?) : RecyclerViewAdapter<ReportCard, ReportHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHolder {
        val holder = ReportHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_report, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: ReportHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun setItems(items: List<ReportCard>) {
        val diffs = DiffUtil.calculateDiff(ReportDiffCallback(getItems(), items), !this.getItems().isEmpty())
        super.setItems(items)
        diffs.dispatchUpdatesTo(this)
    }

}