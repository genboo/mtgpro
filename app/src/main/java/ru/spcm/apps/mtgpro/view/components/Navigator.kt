package ru.spcm.apps.mtgpro.view.components

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ru.spcm.apps.mtgpro.view.fragments.CardFragment
import ru.spcm.apps.mtgpro.view.fragments.SetsFragment
import ru.spcm.apps.mtgpro.view.fragments.SpoilersFragment
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class Navigator(private val activity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun createFragment(screenKey: String, data: Any?): Fragment {
        when (screenKey) {
            SCREEN_SETS ->
                return SetsFragment()
            SCREEN_SPOILERS ->
                if (data is Array<*>) {
                    return SpoilersFragment.getInstance(data[0] as String, data[1] as String)
                }
            SCREEN_CARD ->
                return CardFragment.getInstance(data as String)
        }
        return SetsFragment()
    }

    override fun showSystemMessage(message: String) {

    }

    override fun exit() {
        activity.finish()
    }

    fun goToSets() {
        applyCommands(arrayOf(Replace(SCREEN_SETS, "")))
    }

    fun goToSpoilers(set: String, name: String) {
        applyCommand(Forward(SCREEN_SPOILERS, arrayOf(set, name)))
    }

    fun goToCard(id: String) {
        applyCommand(Forward(SCREEN_CARD, id))
    }

    fun backTo() {
        applyCommand(Back())
    }

    companion object {
        const val SCREEN_SETS = "screen_sets"
        const val SCREEN_SPOILERS = "screen_spoilers"
        const val SCREEN_CARD = "screen_card"
    }

}