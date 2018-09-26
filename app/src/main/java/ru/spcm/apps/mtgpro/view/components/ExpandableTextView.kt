package ru.spcm.apps.mtgpro.view.components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import ru.spcm.apps.mtgpro.R

/**
 * Спойлер
 * Created by gen on 13.12.2017.
 */

class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : AppCompatTextView(context, attrs, defStyle) {

    private val mAnimationSpeed: Int
    private val mMaxLines: Int
    private var mAnimating: Boolean = false
    private var mExpanded: Boolean = false
    private var mCollapsedHeight: Int = 0

    private val mAnimationInterpolator: TimeInterpolator

    private var mListener: OnExpandListener? = null

    private var mSavedState: SavedState? = null

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyle, 0)
        mAnimationSpeed = attributes.getInt(R.styleable.ExpandableTextView_animation_speed, DEFAULT_ANIMATION_SPEED)
        attributes.recycle()

        mMaxLines = maxLines

        mAnimationInterpolator = AccelerateDecelerateInterpolator()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        if (mSavedState != null) {
            mExpanded = mSavedState!!.expanded
            if (mExpanded) {
                maxHeight = Integer.MAX_VALUE
                maxLines = Integer.MAX_VALUE
            }
            mSavedState = null
        }
        if (mMaxLines == 0 && !mExpanded && !mAnimating) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun toggle(): Boolean {
        return if (mExpanded) collapse() else expand()
    }

    fun expand(): Boolean {
        if (!mExpanded && !mAnimating && mMaxLines >= 0) {
            notifyOnExpand()
            measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            mCollapsedHeight = measuredHeight
            mAnimating = true

            maxLines = Integer.MAX_VALUE

            measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            val expandedHeight = measuredHeight

            val valueAnimator = ValueAnimator.ofInt(mCollapsedHeight, expandedHeight)
            valueAnimator.addUpdateListener { animation -> height = animation.animatedValue as Int }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    maxHeight = Integer.MAX_VALUE
                    minHeight = 0

                    val layoutParams = layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    setLayoutParams(layoutParams)

                    mExpanded = true
                    mAnimating = false
                }
            })
            valueAnimator.interpolator = mAnimationInterpolator
            valueAnimator
                    .setDuration(mAnimationSpeed.toLong())
                    .start()

            return true
        }

        return false
    }

    fun collapse(): Boolean {
        if (mExpanded && !mAnimating && mMaxLines >= 0) {
            notifyOnCollapse()
            val expandedHeight = this.measuredHeight
            mAnimating = true

            val valueAnimator = ValueAnimator.ofInt(expandedHeight, mCollapsedHeight)
            valueAnimator.addUpdateListener { animation -> height = animation.animatedValue as Int }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mExpanded = false
                    mAnimating = false

                    maxLines = mMaxLines

                    val layoutParams = layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    setLayoutParams(layoutParams)
                }
            })

            valueAnimator.interpolator = mAnimationInterpolator
            valueAnimator
                    .setDuration(mAnimationSpeed.toLong())
                    .start()

            return true
        }

        return false
    }

    private fun notifyOnExpand() {
        if (mListener != null) {
            mListener!!.onExpand(this)
        }
    }

    private fun notifyOnCollapse() {
        if (mListener != null) {
            mListener!!.onCollapse(this)
        }
    }

    fun setExpandListener(listener: OnExpandListener) {
        mListener = listener
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()
        val ss = SavedState(parcelable)
        ss.expanded = mExpanded
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)
        mSavedState = state
    }

    private class SavedState : View.BaseSavedState {

        internal var expanded: Boolean = false

        internal constructor(superState: Parcelable?) : super(superState)

        private constructor(`in`: Parcel) : super(`in`) {
            expanded = `in`.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (expanded) 1 else 0)
        }

        companion object {

            @Suppress("unused")
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }


    }

    interface OnExpandListener {
        /**
         * The [ExpandableTextView] is being expanded.
         *
         * @param view the textview
         */
        fun onExpand(view: ExpandableTextView)

        /**
         * The [ExpandableTextView] is being collapsed.
         *
         * @param view the textview
         */
        fun onCollapse(view: ExpandableTextView)
    }

    companion object {

        private const val DEFAULT_ANIMATION_SPEED = 100
    }

}