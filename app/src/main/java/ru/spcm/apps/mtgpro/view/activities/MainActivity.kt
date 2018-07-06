package ru.spcm.apps.mtgpro.view.activities

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
import ru.spcm.apps.mtgpro.view.components.BottomNavigationViewHelper
import ru.spcm.apps.mtgpro.view.components.Navigator

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var component: AppComponent? = null

    val navigator by lazy { Navigator(this, supportFragmentManager, R.id.content) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawableResource(R.drawable.transparent)
        if (component == null) {
            component = (application as App).appComponent
            component?.inject(this)
        }

        bottomMenu.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            navigator.goToSets()
            bottomMenu.selectedItemId = R.id.nav_sets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        BottomNavigationViewHelper.removeShiftMode(bottomMenu)
    }

    fun getView(): View {
        return drawerLayout
    }

    fun getFab(): FloatingActionButton {
        return fab
    }

    override fun onBackPressed() {
        navigator.backTo()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(bottomMenu.selectedItemId != item.itemId) {
            when (item.itemId) {
                R.id.nav_sets -> navigator.goToSets()
                R.id.nav_collection -> navigator.goToCollection()
                R.id.nav_wish_list -> navigator.goToWishList()
                R.id.nav_libraries -> navigator.goToLibraries()
                R.id.nav_search -> navigator.goToLibraries()
            }
        }
        return true
    }

}