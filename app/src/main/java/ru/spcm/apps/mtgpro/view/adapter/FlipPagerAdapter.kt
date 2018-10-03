package ru.spcm.apps.mtgpro.view.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache


class FlipPagerAdapter(val context: Context, items: List<CardLocal>?) : PagerAdapter() {

    private var items: List<CardLocal>
    private var inflater: LayoutInflater
    private var listener: (CardLocal) -> Unit = { }

    init {
        if (items == null) {
            this.items = ArrayList()
        } else {
            this.items = items
        }
        inflater = LayoutInflater.from(context)
    }

    override fun instantiateItem(viewGroup: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.fragment_image, viewGroup, false)
        val imageView = view.findViewById(R.id.cardImage) as ImageView
        imageView.loadImageFromCache(items[position].card.imageUrl)
        imageView.setOnClickListener { listener(items[position]) }
        ViewCompat.setTransitionName(imageView, items[position].card.id)
        viewGroup.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as FrameLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun setItems(items: List<CardLocal>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (CardLocal) -> Unit = { }) {
        this.listener = listener
    }

    override fun getCount(): Int {
        return items.size
    }
}