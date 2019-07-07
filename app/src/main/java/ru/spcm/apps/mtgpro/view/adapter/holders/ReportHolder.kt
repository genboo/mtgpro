package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import kotlinx.android.synthetic.main.fragment_price_volatility.*
import kotlinx.android.synthetic.main.list_item_report.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.tools.format
import ru.spcm.apps.mtgpro.tools.formatRound
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache

class ReportHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: CardObserved) = with(itemView) {
        cardVol.text = item.diff.format()
        when {
            item.diff == 0f -> cardVol.setTextColor(ContextCompat.getColor(cardVol.context, R.color.colorTextMain))
            item.diff < 0 -> cardVol.setTextColor(ContextCompat.getColor(cardVol.context, R.color.colorNegative))
            else -> cardVol.setTextColor(ContextCompat.getColor(cardVol.context, R.color.colorPositive))
        }
        val drawable: Drawable? = when {
            item.diff > 0 -> ContextCompat.getDrawable(cardVol.context, R.drawable.ic_diff_up)
            item.diff < 0 -> ContextCompat.getDrawable(cardVol.context, R.drawable.ic_diff_down)
            else -> null
        }
        cardVol.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

        val price = item.price ?: 0f
        val priceFormatted = if (price > 100) price.formatRound() else price.format()

        val stringBuilder = SpannableStringBuilder(cardPrice.context.getString(R.string.price_rub, priceFormatted))
        stringBuilder.setSpan(RelativeSizeSpan(0.8f),
                priceFormatted.length,
                stringBuilder.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        cardPrice.text = stringBuilder

        cardImage.loadImageFromCache(item.imageUrl)
        cardImage.colorFilter = when {
            item.observe && price > item.top -> PorterDuffColorFilter(ContextCompat.getColor(cardImage.context, R.color.colorGreen), PorterDuff.Mode.MULTIPLY)
            item.observe && price < item.bottom -> PorterDuffColorFilter(ContextCompat.getColor(cardImage.context, R.color.colorRed), PorterDuff.Mode.MULTIPLY)
            else -> null
        }
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }

}