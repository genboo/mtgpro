package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.transition.TransitionInflater
import android.view.View
import kotlinx.android.synthetic.main.layout_app_bar_main.*
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.view.components.Navigator


abstract class BaseFragment : Fragment() {

    var component: AppComponent? = null

    val args: Bundle by lazy { arguments ?: Bundle() }

    val navigator: Navigator by lazy { (activity as MainActivity).navigator }

    protected fun updateToolbar() {
        if (toolbar != null) {
            toolbar.title = getTitle()
        }
    }

    protected fun initFragment() {
        if (component == null) {
            component = (activity?.application as App).appComponent
            inject()
        }
    }

    protected fun showSnack(text: Int, action: View.OnClickListener?) {
        if (view != null) {
            val snackBar = Snackbar.make(view as View, text, Snackbar.LENGTH_SHORT)
            if (action != null) {
                snackBar.setAction(R.string.action_cancel, action)
            }
            snackBar.show()
        }
    }

    open fun updateTitle(title: String) {
        toolbar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (lifecycle as? LifecycleRegistry)?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (lifecycle as? LifecycleRegistry)?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    internal abstract fun inject()

    abstract fun getTitle(): String
}