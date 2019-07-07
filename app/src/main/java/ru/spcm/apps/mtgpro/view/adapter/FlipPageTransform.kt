package ru.spcm.apps.mtgpro.view.adapter

import androidx.viewpager.widget.ViewPager
import android.view.View

class FlipPageTransform : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val rotation = 180f * position
        val visibility = if (rotation > 90f || rotation < -90f) View.INVISIBLE else View.VISIBLE

        view.visibility = visibility
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.rotationY = rotation
        view.translationX = -view.width * position

    }

}