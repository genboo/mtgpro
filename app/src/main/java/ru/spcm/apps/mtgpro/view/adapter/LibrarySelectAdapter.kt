package ru.spcm.apps.mtgpro.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo

class LibrarySelectAdapter(context: Context, private val objects: List<LibraryInfo>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(i: Int): Any {
        return objects[i]
    }

    override fun getItemId(i: Int): Long {
        return objects[i].id
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        val view: View = convertView
                ?: inflater.inflate(R.layout.list_item_simple_text, viewGroup, false)

        val name = view.findViewById<TextView>(R.id.tv_text)
        name.text = objects[i].name
        return view
    }
}