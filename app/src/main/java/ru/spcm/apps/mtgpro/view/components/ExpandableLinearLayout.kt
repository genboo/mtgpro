package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.transition.Transition
import android.util.AttributeSet
import android.widget.LinearLayout

class ExpandableLinearLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var collapsedHeight = 0
    private var isExpanded = false
    private var isAnimated = false

    private val listener = object : ExpandListener() {
        override fun onTransitionEnd(transition: Transition) {
            isAnimated = false
            transition.removeListener(this)
        }

        override fun onTransitionStart(transition: Transition) {
            isAnimated = true
        }

    }

    fun toggle() {
        if (!isAnimated) {
            val params = layoutParams
            if (isExpanded) {
                params.height = collapsedHeight
            } else {
                collapsedHeight = height
                params.height = 500
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