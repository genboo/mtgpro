package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent



class CardViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var listener: (Int) -> Unit = { _ -> }
    var isSwipeable = true

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isSwipeable) {
            super.onTouchEvent(event)
        } else {
            performClick()
            false
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isSwipeable) {
            super.onInterceptTouchEvent(event)
        } else {
            false
        }

    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun setOnPageSelectedListener(listener: (Int) -> Unit = { _ -> }) {
        this.listener = listener
    }

}