package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_wish_filter.view.*
import ru.spcm.apps.mtgpro.model.tools.Icons
import ru.spcm.apps.mtgpro.model.dto.SetName

class WishFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: SetName) = with(itemView) {
        selected.text = item.setTitle
        setIcon.setImageDrawable(setIcon.context.getDrawable(Icons.getIcon(item.set)))
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }

}