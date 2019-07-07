package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent



class InterceptedNestedScrollView(context: Context, attrs:AttributeSet):NestedScrollView(context, attrs){

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> super.onTouchEvent(ev)

            MotionEvent.ACTION_MOVE -> return false

            MotionEvent.ACTION_CANCEL -> super.onTouchEvent(ev)

            MotionEvent.ACTION_UP -> return false
        }

        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)
        return true
    }
}