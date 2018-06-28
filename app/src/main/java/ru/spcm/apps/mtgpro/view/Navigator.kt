package ru.spcm.apps.mtgpro.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Replace

class Navigator(private val activity:FragmentActivity, fragmentManager: FragmentManager, containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun createFragment(screenKey: String, data: Any?): Fragment {
        when(screenKey){
            SCREEN_SETS -> return SetsFragment()
        }
        return SetsFragment()
    }

    override fun showSystemMessage(message: String) {

    }

    override fun exit() {
        activity.finish()
    }

    fun goToSets(){
        applyCommands(arrayOf(Replace(SCREEN_SETS, "")))
    }

    companion object {
        const val SCREEN_SETS = "screen_sets"
    }

}