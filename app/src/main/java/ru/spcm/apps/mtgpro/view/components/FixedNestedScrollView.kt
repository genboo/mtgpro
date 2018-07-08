package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.widget.OverScroller
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import java.lang.reflect.Field


/**
 * Created by gen on 07.07.2018.
 */
class FixedNestedScrollView(context: Context, attrs: AttributeSet) : NestedScrollView(context, attrs) {

    private val mScroller: OverScroller?
    var isFling = false

    private val overScroller: OverScroller?
        get() {
            val fs: Field?
            try {
                fs = this.javaClass.superclass.getDeclaredField("mScroller")
                fs!!.isAccessible = true
                return fs.get(this) as OverScroller
            } catch (t: Throwable) {
                return null
            }

        }

    init {
        mScroller = overScroller
    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)

        // here we effectively extend the super class functionality for backwards compatibility and just call invalidateOnAnimation()
        if (childCount > 0) {
            ViewCompat.postInvalidateOnAnimation(this)

            // Initializing isFling to true to track fling action in onScrollChanged() method
            isFling = true
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        if (isFling) {
            if (Math.abs(t - oldt) <= 3 || t == 0 || t == getChildAt(0).measuredHeight - measuredHeight) {
                isFling = false

                // This forces the mFinish variable in scroller to true (as explained the
                //    mentioned link above) and does the trick
                mScroller?.abortAnimation()
            }
        }
    }


}