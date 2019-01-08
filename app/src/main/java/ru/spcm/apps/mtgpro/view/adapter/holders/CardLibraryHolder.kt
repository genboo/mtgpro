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
import kotlinx.android.synthetic.main.layout_card_header.view.*
import kotlinx.android.synthetic.main.list_item_card.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.tools.format
import ru.spcm.apps.mtgpro.view.adapter.CardListItem
import ru.spcm.apps.mtgpro.view.components.loadImage
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import java.util.*

class CardLibraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardImage: View? = itemView.cardImage

    fun bind(item: CardListItem) = with(itemView) {

        when {
            item.data == null -> {
                headerName.text = item.title
            }
            else -> {
                val card = item.data.card
                cardName.text = card.name
                cardRarity.setColorFilter(ContextCompat.getColor(cardRarity.context, card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
                cardRarity.setImageDrawable(cardRarity.context.getDrawable(card.getSetIcon()))
                cardSet.text = card.setTitle
                cardType.text = card.type

                val count = SpannableStringBuilder(String.format(Locale.getDefault(), "Кол-во: %d", card.count))
                val countStart = count.indexOf(card.count.toString())
                val countEnd = countStart + card.count.toString().length
                count.setSpan(ForegroundColorSpan(ContextCompat.getColor(cardCount.context, R.color.colorPrimary)), countStart, countEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                count.setSpan(RelativeSizeSpan(1.1f), countStart, countEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                cardCount.text = count
                cardNumber.text = String.format(Locale.getDefault(), "%s %s", card.set, card.numberFormatted)

                ViewCompat.setTransitionName(cardImage, card.id)
                cardImage.loadImageFromCache(card.getImage(context.getString(R.string.secondary_image_url)))

                if (item.data.price == null) {
                    cardPrice.visibility = View.GONE
                } else {
                    cardPrice.text = cardPrice.context.getString(R.string.price_rub, item.data.price?.format())
                    cardPrice.visibility = View.VISIBLE
                }
            }
        }
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

}