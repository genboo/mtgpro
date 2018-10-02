package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.transition.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import ru.spcm.apps.mtgpro.R

class ExpandableLinearLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {


    private val shadow = FrameLayout(context)
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
        maxHeight = 350 * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
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

            makeAnimation()
            if(isExpanded){
                shadow.visibility = View.VISIBLE
            }else{
                shadow.visibility = View.GONE
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent is ViewGroup) {
            val group = parent as ViewGroup
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            shadow.layoutParams = params
            shadow.background = resources.getDrawable(R.drawable.shadow, context.theme)
            shadow.visibility = View.GONE
            var index = 0
            for (i in 0 until group.childCount) {
                if (group.getChildAt(i).id == this.id) {
                    index = i
                    break
                }
            }
            if(shadow.parent == null) {
                group.addView(shadow, index)
            }
            shadow.setOnClickListener { toggle() }
        }
    }

    private fun makeAnimation() {
        val transition = TransitionSet()
        val resize = ChangeBounds()
        resize.excludeTarget(shadow, true)
        resize.addListener(listener)

        val fade = Fade()
        fade.excludeTarget(this, true)

        transition.addTransition(resize)
        transition.addTransition(fade)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
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