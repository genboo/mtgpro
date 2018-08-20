package ru.spcm.apps.mtgpro.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.spcm.apps.mtgpro.App
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.services.AlarmReceiver
import ru.spcm.apps.mtgpro.view.components.BottomNavigationViewHelper
import ru.spcm.apps.mtgpro.view.components.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator by lazy { Navigator(this, supportFragmentManager, R.id.content) }

    private lateinit var settingsItemMenu: MenuItem

    private var component: AppComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (component == null) {
            component = (application as App).appComponent
            component?.inject(this)
        }

        bottomMenu.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            navigator.goToCollection()
            bottomMenu.selectedItemId = R.id.nav_collection
        }

        window.setBackgroundDrawable(null)
        BottomNavigationViewHelper.removeShiftMode(bottomMenu)
    }

    fun getView(): View {
        return drawerLayout
    }

    fun getFab(): FloatingActionButton {
        return fab
    }

    fun getBottomMenu(): BottomNavigationView {
        return bottomMenu
    }

    override fun onNewIntent(intent: Intent) {
        when(intent.getStringExtra(AlarmReceiver.LAUNCH_FRAGMENT)){
            AlarmReceiver.LAUNCH_FRAGMENT_REPORT -> navigator.goToReport()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        settingsItemMenu = menu.findItem(R.id.nav_settings)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> navigator.goToSettings()
            R.id.nav_watch -> navigator.goToWatch()
            R.id.nav_report -> navigator.goToReport()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sets -> navigator.goToSets()
            R.id.nav_collection -> navigator.goToCollection()
            R.id.nav_wish_list -> navigator.goToWishList()
            R.id.nav_libraries -> navigator.goToLibraries()
            R.id.nav_search -> navigator.goToSearch()
        }
        return true
    }

    override fun onBackPressed() {
        navigator.backTo()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}