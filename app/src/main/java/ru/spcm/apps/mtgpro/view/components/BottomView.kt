package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.MenuInflater
import android.widget.FrameLayout
import ru.spcm.apps.mtgpro.R

/**
 * Created by gen on 15.07.2018.
 */
class BottomView : FrameLayout {

    private var menuInflater: MenuInflater = MenuInflater(context)

    private var showText = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BottomView, defStyleAttr, 0)
        val menuResource = attributes.getResourceId(R.styleable.BottomView_menu_resource, 0)
        showText = attributes.getBoolean(R.styleable.BottomView_show_text, true)

        attributes.recycle()

    }

}