package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import kotlinx.android.synthetic.main.list_item_card_watched.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.tools.format
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import java.util.*

class CardWatchedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View = itemView.cardImage

    fun bind(item: CardWatched) = with(itemView) {
        cardName.text = item.card.name
        cardRarity.setColorFilter(ContextCompat.getColor(context, item.card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
        cardRarity.setImageDrawable(context.getDrawable(item.card.getSetIcon()))
        cardSet.text = item.card.setTitle

        val priceString = item.price.format()
        val price = SpannableStringBuilder(cardPrice.context.getString(R.string.price_rub, priceString))
        price.setSpan(RelativeSizeSpan(1.3f), 0, priceString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        cardPrice.text = price

        cardNumber.text = String.format(Locale.getDefault(), "%s %s", item.card.set, item.card.numberFormatted)

        ViewCompat.setTransitionName(cardImage, item.card.id)
        cardImage.loadImageFromCache(item.card.getImage(context.getString(R.string.secondary_image_url)))
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

    fun setDeleteListener(listener: View.OnClickListener) {
        itemView.delete?.setOnClickListener(listener)
    }

}