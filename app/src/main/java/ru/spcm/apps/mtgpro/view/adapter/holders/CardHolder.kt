package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_card.view.*
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import java.util.*

class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View = itemView.cardImage

    fun bind(item: Card) = with(itemView) {

        cardName.text = item.name
        cardRarity.setColorFilter(ContextCompat.getColor(context, item.getSetIconColor()), PorterDuff.Mode.SRC_IN)
        cardRarity.setImageDrawable(context.getDrawable(item.getSetIcon()))
        cardSet.text = item.setCode
        cardType.text = item.type
        cardCount.text = String.format(Locale.getDefault(), "Кол-во: %d", item.count)
        cardNumber.text = String.format(Locale.getDefault(), "%s %s", item.set, item.numberFormatted)

        ViewCompat.setTransitionName(cardImage, item.id)
        cardImage.loadImageFromCache(item.imageUrl)

    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}