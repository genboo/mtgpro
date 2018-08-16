package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.*
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.transition.TransitionInflater
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.layout_app_bar_main.*
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
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
            inject()
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

    fun toggleBottomMenu(visible: Boolean) {
        val bottomMenu = (activity as MainActivity).getBottomMenu()
        if (visible) {
            bottomMenu.visibility = View.VISIBLE
        } else {
            bottomMenu.visibility = View.GONE
        }
    }

    fun toggleAppBar(visible: Boolean){
        if(visible){
            appBar.visibility = View.VISIBLE
        }else{
            appBar.visibility = View.GONE
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if(this is SettingsFragment
                || this is WatchFragment
                || this is PriceVolatilityFragment) {
            val sett = menu.findItem(R.id.nav_settings)
            sett.isVisible = false
            val watch = menu.findItem(R.id.nav_watch)
            watch.isVisible = false
        }
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