package ru.spcm.apps.mtgpro.view.activities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.services.AlarmReceiver
import ru.spcm.apps.mtgpro.tools.Settings
import ru.spcm.apps.mtgpro.view.components.BottomNavigationViewHelper
import ru.spcm.apps.mtgpro.view.components.Navigator
import ru.spcm.apps.mtgpro.viewmodel.MainViewModel
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val settings = Settings()

    val navigator by lazy { Navigator(this, supportFragmentManager, R.id.content) }

    private var component: AppComponent? = null

    private val settingLoaderObserver = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (component == null) {
            component = (application as App).appComponent
            component?.inject(this)
        }

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.settings.observe(this, Observer { observeSettings(it) })
        viewModel.updateCacheType()

        bottomMenu.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            settingLoaderObserver.observe(this, Observer {
                if (it != null && it) {
                    settingLoaderObserver.removeObservers(this)
                    navigator.goToCollection()
                    bottomMenu.selectedItemId = R.id.nav_collection
                }
            })

        }

        window.setBackgroundDrawable(null)
        BottomNavigationViewHelper.removeShiftMode(bottomMenu)
    }

    private fun observeSettings(data: List<Setting>?) {
        if (data != null) {
            settings.updateSettings(data)
            settingLoaderObserver.postValue(true)
        }
    }

    fun getView(): View {
        return drawerLayout
    }

    fun getFab(): FloatingActionButton {
        return fab
    }

    fun getSettings(): Settings {
        return settings
    }

    fun getBottomMenu(): BottomNavigationView {
        return bottomMenu
    }

    override fun onNewIntent(intent: Intent) {
        when (intent.getStringExtra(AlarmReceiver.LAUNCH_FRAGMENT)) {
            AlarmReceiver.LAUNCH_FRAGMENT_REPORT -> navigator.goToReport()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sets -> navigator.goToSets()
            R.id.nav_collection -> navigator.goToCollection()
            R.id.nav_wish_list -> navigator.goToWishList()
            R.id.nav_search -> navigator.goToSearch()
            R.id.nav_more -> navigator.goToMore()
        }
        return true
    }

    override fun onBackPressed() {
        navigator.backTo()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}