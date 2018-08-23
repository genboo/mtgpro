package ru.spcm.apps.mtgpro.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.view.fragments.ImageFragment

class FlipPagerAdapter (fm: FragmentManager, items: List<CardLocal>?) : FragmentStatePagerAdapter(fm) {

    private var items: List<CardLocal>

    init {
        if (items == null) {
            this.items = ArrayList()
        } else {
            this.items = items
        }
    }

    fun setItems(items: List<CardLocal>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return ImageFragment.getInstance(items[position].card.imageUrl, items[position].card.id)
    }

    override fun getCount(): Int {
        return items.size
    }
}