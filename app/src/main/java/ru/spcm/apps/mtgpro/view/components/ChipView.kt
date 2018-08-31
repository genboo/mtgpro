package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import ru.spcm.apps.mtgpro.R

class ChipView(context: Context) : CheckBox(context) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_chip_checkable)
        buttonDrawable = ContextCompat.getDrawable(context, R.drawable.bg_chip_checkbox)
        buttonTintList = ColorStateList(
                arrayOf(
                        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
                        intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked)
                ),
                intArrayOf(
                        ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context, R.color.colorTextHint)
                )
        )

        val paddingVertical = context.resources.getDimensionPixelSize(R.dimen.chip_padding_vertical)
        val paddingHorizontal = context.resources.getDimensionPixelSize(R.dimen.chip_padding_horizontal)
        val offset = context.resources.getDimensionPixelSize(R.dimen.chip_offset)

        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.marginEnd = offset
        params.bottomMargin = offset
        layoutParams = params
    }

}