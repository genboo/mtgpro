package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_set.view.*
import ru.spcm.apps.mtgpro.model.dto.Icons
import ru.spcm.apps.mtgpro.model.dto.Set

class SetHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Set) = with(itemView) {
        setName.text = item.name
        setIcon.setImageDrawable(setIcon.context.getDrawable(Icons.getIcon(item.code)))
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }

}