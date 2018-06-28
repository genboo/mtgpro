package ru.spcm.apps.mtgpro

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ru.spcm.apps.mtgpro.di.components.AppComponent
import ru.spcm.apps.mtgpro.view.Navigator

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var component: AppComponent? = null

    private val navigator by lazy { Navigator(this, supportFragmentManager, R.id.content) }

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
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_sets -> navigator.goToSets()
        }
        return true
    }

}