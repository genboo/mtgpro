package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class CardViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var listener: (Int) -> Unit = { _ -> }

    init {
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                listener(position)
            }
        })
    }

    fun setOnPageSelectedListener(listener: (Int) -> Unit = { _ -> }) {
        this.listener = listener
    }

}