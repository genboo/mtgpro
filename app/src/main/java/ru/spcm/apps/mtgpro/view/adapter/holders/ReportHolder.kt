package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_report.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.ReportCard
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache

class ReportHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: ReportCard) = with(itemView) {
        cardName.text = item.name
        cardDiff.text = item.diff
        when {
            item.diff.toFloat() == 0f -> cardDiff.setTextColor(ContextCompat.getColor(cardDiff.context, R.color.colorNoColor))
            item.diff.toFloat() < 0 -> cardDiff.setTextColor(ContextCompat.getColor(cardDiff.context, R.color.colorRed))
            else -> cardDiff.setTextColor(ContextCompat.getColor(cardDiff.context, R.color.colorGreen))
        }

        cardImage.loadImageFromCache(item.imageUrl)
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }

}