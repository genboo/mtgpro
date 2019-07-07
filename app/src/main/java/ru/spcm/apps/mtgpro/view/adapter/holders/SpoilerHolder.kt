package ru.spcm.apps.mtgpro.view.adapter.holders

import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_loading.view.*
import kotlinx.android.synthetic.main.list_item_spoiler.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache

class SpoilerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Card) = with(itemView) {
        cardRarity.setColorFilter(ContextCompat.getColor(cardRarity.context, item.getSetIconColor()), PorterDuff.Mode.SRC_IN)
        cardRarity.setImageDrawable(cardRarity.context.getDrawable(item.getSetIcon()))
        cardNumber.text = String.format("%s %s", item.set, item.numberFormatted)

        cardExists.text = ""
        cardExists.visibility = View.INVISIBLE
        cardImage.loadImageFromCache(item.getImage(context.getString(R.string.secondary_image_url)))

        if (item.count == 0) {
            cardExists.visibility = View.INVISIBLE
        } else {
            cardExists.text = String.format("%s", item.count)
            cardExists.visibility = View.VISIBLE
        }
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock?.setOnClickListener(listener)
    }

    fun switchLoading(loading: Boolean) = with(itemView) {
        loadingBlock.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }
}