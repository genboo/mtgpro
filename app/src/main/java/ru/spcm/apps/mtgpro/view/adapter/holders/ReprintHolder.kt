package ru.spcm.apps.mtgpro.view.adapter.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_reprint.view.*
import ru.spcm.apps.mtgpro.model.tools.Icons

class ReprintHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: String) = with(itemView) {
        val icon = setName.context.resources
                .getDrawable(Icons.getIcon(item), setName.context.theme)
        setName.text = item
        setName.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
    }

    fun setListener(listener: View.OnClickListener) {
        itemView.itemBlock.setOnClickListener(listener)
    }
}