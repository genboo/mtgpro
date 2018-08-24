package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import kotlinx.android.synthetic.main.list_item_report.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.ReportCard
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache

class ReportHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: ReportCard) = with(itemView) {
        cardViol.text = item.diff
        when {
            item.diff.toFloat() == 0f -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorTextMain))
            item.diff.toFloat() < 0 -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorNegative))
            else -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorPositive))
        }

        val stringBuilder = SpannableStringBuilder(cardPrice.context.getString(R.string.price_usd, item.price))
        stringBuilder.setSpan(RelativeSizeSpan(0.8f), item.price.length, stringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        cardPrice.text = stringBuilder

        cardImage.loadImageFromCache(item.imageUrl)
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }

}