package ru.spcm.apps.mtgpro.view.components

import android.os.Build
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator

fun View.fadeIn() {
    TransitionManager.beginDelayedTransition(parent as ViewGroup, Fade())
    this.visibility = View.VISIBLE
}

fun View.fadeOut() {
    TransitionManager.beginDelayedTransition(parent as ViewGroup, Fade())
    this.visibility = View.GONE
}

fun View.slideIn(edge: Int) {
    TransitionManager.beginDelayedTransition(parent as ViewGroup, Slide(edge))
    this.visibility = View.VISIBLE
}

fun View.slideOut(edge: Int) {
    TransitionManager.beginDelayedTransition(parent as ViewGroup, Slide(edge))
    this.visibility = View.GONE
}

fun View.expand(listener: Transition.TransitionListener? = null) {
    val transition = ChangeBounds()
    if (listener != null) {
        transition.addListener(listener)
    }
    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
}

fun View.blink(){
    val view = this
    val fade = Fade()
    fade.addListener( object: Transition.TransitionListener {

        override fun onTransitionEnd(transition: Transition) {
            transition.removeListener(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                TransitionManager.endTransitions(parent as ViewGroup)
            }
            TransitionManager.beginDelayedTransition(parent as ViewGroup, Fade())
            view.visibility = View.VISIBLE
        }

        override fun onTransitionStart(transition: Transition) {}

        override fun onTransitionResume(transition: Transition) {}

        override fun onTransitionPause(transition: Transition) {}

        override fun onTransitionCancel(transition: Transition) {}

    })
    TransitionManager.beginDelayedTransition(parent as ViewGroup, fade)
    this.visibility = View.GONE
}

fun ViewGroup.toggleSlideChilds(visibility: Int, edge: Int, vararg views: View) {
    val transition = TransitionSet()
    var delay: Long = 0
    for (view in views) {
        val slide = Slide(edge)
        slide.interpolator = AnticipateOvershootInterpolator()
        slide.addTarget(view).startDelay = delay
        transition.addTransition(slide)
        delay += 100
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        TransitionManager.endTransitions(this)
    }
    TransitionManager.beginDelayedTransition(this, transition)
    for (view in views) {
        view.visibility = visibility
    }
}


