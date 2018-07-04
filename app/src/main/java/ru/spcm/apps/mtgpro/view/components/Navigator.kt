package ru.spcm.apps.mtgpro.view.components

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.fragments.*
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.*

class Navigator(private val activity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun createFragment(screenKey: String, data: Any?): Fragment {
        when (screenKey) {
            SCREEN_SETS -> return SetsFragment()
            SCREEN_COLLECTION -> return CollectionFragment()
            SCREEN_LIBRARIES -> return LibrariesFragment()
            SCREEN_WISH_LIST -> return WishFragment()
            SCREEN_SPOILERS ->
                if (data is Array<*>) {
                    return SpoilersFragment.getInstance(data[0] as String, data[1] as String)
                }
            SCREEN_CARD -> return CardFragment.getInstance(data as String)
        }
        return SetsFragment()
    }

    override fun showSystemMessage(message: String) {

    }

    override fun exit() {
        activity.finish()
    }

    override fun setupFragmentTransactionAnimation(command: Command,
                                                   currentFragment: Fragment?,
                                                   nextFragment: Fragment?,
                                                   fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right_2)
    }

    fun goToCollection() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_COLLECTION, "")))
    }

    fun goToWishList() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_WISH_LIST, "")))
    }

    fun goToSets() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_SETS, "")))
    }

    fun goToLibraries() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_LIBRARIES, "")))
    }

    fun goToSpoilers(set: String, name: String) {
        applyCommand(Forward(SCREEN_SPOILERS, arrayOf(set, name)))
    }

    fun goToCard(id: String) {
        applyCommand(Forward(SCREEN_CARD, id))
    }

    fun goToLibrary(id: String) {
        applyCommand(Forward(SCREEN_CARD, id))
    }


    fun backTo() {
        applyCommand(Back())
    }

    companion object {
        const val SCREEN_SETS = "screen_sets"
        const val SCREEN_SPOILERS = "screen_spoilers"
        const val SCREEN_CARD = "screen_card"
        const val SCREEN_COLLECTION = "screen_collection"
        const val SCREEN_WISH_LIST = "screen_wish_list"
        const val SCREEN_LIBRARIES = "screen_libraries"
    }

}