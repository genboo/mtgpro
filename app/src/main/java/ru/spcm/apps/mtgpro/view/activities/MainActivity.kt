package ru.spcm.apps.mtgpro.view.activities

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
import ru.spcm.apps.mtgpro.view.components.BottomNavigationViewHelper
import ru.spcm.apps.mtgpro.view.components.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var settingsItemMenu: MenuItem

    private var component: AppComponent? = null

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator by lazy { Navigator(this, supportFragmentManager, R.id.content) }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        settingsItemMenu = menu.findItem(R.id.nav_settings)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_settings) {
            navigator.goToSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        navigator.backTo()
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

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}