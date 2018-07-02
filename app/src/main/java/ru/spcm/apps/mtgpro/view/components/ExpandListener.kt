package ru.spcm.apps.mtgpro.view.components

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

class ExpandListener(private val arrow: View) : ExpandableTextView.OnExpandListener {

    override fun onExpand(view: ExpandableTextView) {
        createRotateAnimator(arrow, 0f, 180f).start()
    }

    override fun onCollapse(view: ExpandableTextView) {
        createRotateAnimator(arrow, 180f, 0f).start()
    }

    private fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 200
        animator.interpolator = LinearInterpolator()
        return animator
    }
}
