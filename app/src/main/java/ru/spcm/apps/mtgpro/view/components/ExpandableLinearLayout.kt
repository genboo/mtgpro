package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.transition.Transition
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.LinearLayout

class ExpandableLinearLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var collapsedHeight = 0
    private var isExpanded = false
    private var isAnimated = false

    private var maxHeight = 100

    private val listener = object : ExpandListener() {
        override fun onTransitionEnd(transition: Transition) {
            isAnimated = false
            transition.removeListener(this)
        }

        override fun onTransitionStart(transition: Transition) {
            isAnimated = true
        }

    }

    init {
        val metrics = resources.displayMetrics
        maxHeight = 250 * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun toggle() {
        if (!isAnimated) {
            val params = layoutParams
            if (isExpanded) {
                params.height = collapsedHeight
            } else {
                collapsedHeight = height
                params.height = maxHeight
            }
            isExpanded = !isExpanded
            layoutParams = params
            this.expand(listener)
        }
    }

    abstract class ExpandListener : Transition.TransitionListener {

        override fun onTransitionResume(transition: Transition) {

        }

        override fun onTransitionPause(transition: Transition) {

        }

        override fun onTransitionCancel(transition: Transition) {

        }

    }

}