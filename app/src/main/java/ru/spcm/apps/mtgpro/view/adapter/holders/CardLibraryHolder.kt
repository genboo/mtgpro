package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.layout_card_header.view.*
import kotlinx.android.synthetic.main.layout_library_info.view.*
import kotlinx.android.synthetic.main.list_item_card.view.*
import ru.spcm.apps.mtgpro.view.adapter.CardListItem
import ru.spcm.apps.mtgpro.view.components.loadImage
import java.util.*

class CardLibraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View? = itemView.cardImage

    fun bind(item: CardListItem) = with(itemView) {
        if (item.colorState != null) {
            libraryState.setData(item.manaState ?: arrayListOf(), item.colorState ?: arrayListOf())
        } else if (item.data == null) {
            headerName.text = item.title
        } else {
            val card = item.data.card
            cardName.text = card.name
            cardRarity.setColorFilter(ContextCompat.getColor(cardRarity.context, card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
            cardRarity.setImageDrawable(cardRarity.context.getDrawable(card.getSetIcon()))
            cardSet.text = card.setTitle
            cardType.text = card.type
            cardCount.text = String.format(Locale.getDefault(), "Кол-во: %d", card.count)
            cardNumber.text = String.format(Locale.getDefault(), "%s %s", card.set, card.numberFormatted)

            ViewCompat.setTransitionName(cardImage, card.id)
            cardImage.loadImage(card.imageUrl)

        }
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}