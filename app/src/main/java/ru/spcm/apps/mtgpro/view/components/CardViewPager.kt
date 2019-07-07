package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.os.Parcelable
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import ru.spcm.apps.mtgpro.R


class CardViewPager(context: Context, attrs: AttributeSet) : androidx.viewpager.widget.ViewPager(context, attrs) {

    private var listener: (Int) -> Unit = { _ -> }
    var isSwipeable = true

    init {
        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                listener(position)
            }
        })
    }

    fun getImageView(): ImageView {
        return getChildAt(currentItem).findViewById(R.id.cardImage)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
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