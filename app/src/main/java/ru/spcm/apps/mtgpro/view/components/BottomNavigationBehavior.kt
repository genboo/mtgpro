package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import kotlin.math.max
import kotlin.math.min

class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            updateSnackbar(child, dependency)
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: View, snackbarLayout: Snackbar.SnackbarLayout) {
        if (snackbarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorId = child.id
            params.anchorGravity = Gravity.TOP
            params.gravity = Gravity.TOP
            snackbarLayout.layoutParams = params
        }
    }
}