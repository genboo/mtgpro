package ru.spcm.apps.mtgpro.view.fragments

import androidx.lifecycle.*
import android.os.Build
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.layout_app_bar_main.*
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.tools.Settings
import ru.spcm.apps.mtgpro.view.components.Navigator
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var component: AppComponent? = null

    val args: Bundle by lazy { arguments ?: Bundle() }

    val navigator: Navigator by lazy { (activity as MainActivity).navigator }

    protected fun <T : ViewModel> getViewModel(owner: Fragment, t: Class<T>): T {
        return ViewModelProviders.of(owner, viewModelFactory).get(t)
    }

    protected fun updateToolbar() {
        if (toolbar != null) {
            toolbar.title = getTitle()
            val activity = (activity as MainActivity)
            activity.setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                if (activity.supportFragmentManager.backStackEntryCount > 0) {
                    activity.onBackPressed()
                }
            }
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            if (activity.supportFragmentManager.backStackEntryCount == 0) {
                activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home)
                activity.supportActionBar?.setHomeButtonEnabled(false)
            } else {
                activity.supportActionBar?.setHomeAsUpIndicator(null)
                activity.supportActionBar?.setHomeButtonEnabled(true)
            }
        }
    }

    protected fun initFragment() {
        if (component == null) {
            component = (activity?.application as App).appComponent
            component?.inject(this)
        }
    }

    protected fun showSnack(text: Int, action: View.OnClickListener?) {
        val snackBar = Snackbar.make((activity as MainActivity).getView(), text, Snackbar.LENGTH_LONG)
        if (action != null) {
            snackBar.setAction(R.string.action_cancel, action)
        }
        snackBar.show()
    }

    open fun updateTitle(title: String) {
        toolbar?.title = title
    }

    fun getFab(): FloatingActionButton {
        return (activity as MainActivity).getFab()
    }

    fun getSettings(): Settings {
        return (activity as MainActivity).getSettings()
    }

    fun toggleBottomMenu(visible: Boolean) {
        val bottomMenu = (activity as MainActivity).getBottomMenu()
        if (visible) {
            bottomMenu.visibility = View.VISIBLE
        } else {
            bottomMenu.visibility = View.GONE
        }
    }

    open fun getSharedImage(): ImageView? {
        return null
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

    abstract fun getTitle(): String
}