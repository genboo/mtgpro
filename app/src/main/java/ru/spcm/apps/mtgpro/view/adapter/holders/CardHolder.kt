package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import kotlinx.android.synthetic.main.list_item_card.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import java.util.*

class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View = itemView.cardImage

    fun bind(item: Card) = with(itemView) {

        cardName.text = item.name
        cardRarity.setColorFilter(ContextCompat.getColor(context, item.getSetIconColor()), PorterDuff.Mode.SRC_IN)
        cardRarity.setImageDrawable(context.getDrawable(item.getSetIcon()))
        cardSet.text = item.setTitle
        cardType.text = item.type

        val count = SpannableStringBuilder(String.format(Locale.getDefault(), "Кол-во: %d", item.count))
        val countStart = count.indexOf(item.count.toString())
        val countEnd = countStart + item.count.toString().length
        count.setSpan(ForegroundColorSpan(ContextCompat.getColor(cardCount.context, R.color.colorPrimary)), countStart, countEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        count.setSpan(RelativeSizeSpan(1.1f), countStart, countEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        cardCount.text = count
        cardNumber.text = String.format(Locale.getDefault(), "%s %s", item.set, item.numberFormatted)

        ViewCompat.setTransitionName(cardImage, item.id)
        cardImage.loadImageFromCache(item.imageUrl)

    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}