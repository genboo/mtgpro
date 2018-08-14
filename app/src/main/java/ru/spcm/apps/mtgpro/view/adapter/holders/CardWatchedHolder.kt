package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_card_watched.view.*
import ru.spcm.apps.mtgpro.model.dto.CardWatched
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import java.util.*

class CardWatchedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View = itemView.cardImage

    fun bind(item: CardWatched) = with(itemView) {

        cardName.text = item.card.name
        cardRarity.setColorFilter(ContextCompat.getColor(context, item.card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
        cardRarity.setImageDrawable(context.getDrawable(item.card.getSetIcon()))
        cardSet.text = item.card.setTitle
        cardPrice.text = String.format(Locale.getDefault(), "%s usd", item.price)
        cardNumber.text = String.format(Locale.getDefault(), "%s %s", item.card.set, item.card.numberFormatted)

        ViewCompat.setTransitionName(cardImage, item.card.id)
        cardImage.loadImageFromCache(item.card.imageUrl)

    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}