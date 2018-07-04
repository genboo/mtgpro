package ru.spcm.apps.mtgpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.view.adapter.holders.LibraryHolder

/**
 * Адаптер для списка колод
 * Created by gen on 28.09.2017.
 */

class LibrariesListAdapter(items: List<LibraryInfo>?) : RecyclerViewAdapter<LibraryInfo, LibraryHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        val holder = LibraryHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_library, parent, false))
        holder.setListener(View.OnClickListener { v -> onItemClick(v, holder.adapterPosition) })
        return holder
    }

    override fun onBindViewHolder(holder: LibraryHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }
    
    override fun setItems(items: List<LibraryInfo>) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}
