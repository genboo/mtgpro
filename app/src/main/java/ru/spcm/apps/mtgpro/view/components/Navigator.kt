package ru.spcm.apps.mtgpro.view.components

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.ViewCompat
import android.transition.Fade
import kotlinx.android.synthetic.main.activity_main.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.navigation.android.SupportFragmentNavigator
import ru.spcm.apps.mtgpro.navigation.commands.*
import ru.spcm.apps.mtgpro.view.fragments.*

class Navigator(private val activity: FragmentActivity, private val fragmentManager: FragmentManager, containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun createFragment(screenKey: String, data: Any?): Fragment {
        when (screenKey) {
            SCREEN_SETS -> return SetsFragment()
            SCREEN_SEARCH -> return SearchFragment.getInstance(data as String)
            SCREEN_COLLECTION -> return CollectionFragment()
            SCREEN_LIBRARIES -> return LibrariesFragment()
            SCREEN_WISH_LIST -> return WishFragment()
            SCREEN_SPOILERS ->
                if (data is Array<*>) {
                    return SpoilersFragment.getInstance(data[0] as String, data[1] as String)
                }
            SCREEN_IMAGE ->
                if (data is Array<*>) {
                    return FullScreenImageFragment.getInstance(data[0] as String, data[1] as String)
                }
            SCREEN_CARD -> return CardFragment.getInstance(data as String)
            SCREEN_VOLATILITY -> return PriceVolatilityFragment.getInstance(data as String)
            SCREEN_LIBRARY -> return LibraryFragment.getInstance(data as Long)
            SCREEN_SETTINGS -> return SettingsFragment()
            SCREEN_WATCH -> return WatchFragment()
            SCREEN_REPORT -> return ReportFragment()
            SCREEN_MORE -> return MoreFragment()
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
        if (command is Replace || nextFragment is FullScreenImageFragment) {
            currentFragment?.exitTransition = Fade()
            nextFragment?.enterTransition = Fade()
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right_2)
        }
        val image = (currentFragment as? BaseFragment)?.getSharedImage()
        if (image != null) {
            fragmentTransaction.addSharedElement(image, ViewCompat.getTransitionName(image) ?: "")
        }
    }


    /**
     * Переход назад
     */
    fun comeBack() {
        try {
            val currentFragment = fragmentManager.fragments.last()
            if (!localStackCopy.isNullOrEmpty() || isNotBackFragment(currentFragment)) {
                applyCommands(arrayOf(Back()))
            } else {
                activity.bottomMenu.selectedItemId = R.id.nav_sets
            }
        } catch (ex: Exception) {
            activity.bottomMenu.selectedItemId = R.id.nav_sets
        }
    }

    private fun isNotBackFragment(currentFragment: Fragment): Boolean {
        return (currentFragment is SetsFragment)
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
        applyCommand(Forward(SCREEN_LIBRARIES, ""))
    }

    fun goToSearch() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_SEARCH, "")))
    }

    fun goToMore() {
        applyCommands(arrayOf(BackTo(null), Replace(SCREEN_MORE, "")))
    }

    fun goToSearch(search: String) {
        applyCommands(arrayOf(Forward(SCREEN_SEARCH, search)))
    }

    fun goToSpoilers(set: String, name: String) {
        applyCommands(arrayOf(Forward(SCREEN_SPOILERS, arrayOf(set, name))))
    }

    fun goToCard(id: String) {
        applyCommands(arrayOf(Forward(SCREEN_CARD, id)))
    }

    fun goToLibrary(id: Long) {
        applyCommands(arrayOf(Forward(SCREEN_LIBRARY, id)))
    }

    fun goToSettings() {
        applyCommands(arrayOf(Forward(SCREEN_SETTINGS, null)))
    }

    fun goToReport() {
        applyCommands(arrayOf(Forward(SCREEN_REPORT, null)))
    }

    fun goToWatch() {
        applyCommands(arrayOf(Forward(SCREEN_WATCH, null)))
    }

    fun goToPriceVolatility(id: String) {
        applyCommands(arrayOf(Forward(SCREEN_VOLATILITY, id)))
    }

    fun goToImage(id: String, url: String) {
        applyCommands(arrayOf(Forward(SCREEN_IMAGE, arrayOf(id, url))))
    }

    fun backTo() {
        applyCommands(arrayOf(Back()))
    }

    companion object {
        const val SCREEN_SETS = "screen_sets"
        const val SCREEN_SPOILERS = "screen_spoilers"
        const val SCREEN_CARD = "screen_card"
        const val SCREEN_COLLECTION = "screen_collection"
        const val SCREEN_WISH_LIST = "screen_wish_list"
        const val SCREEN_LIBRARIES = "screen_libraries"
        const val SCREEN_LIBRARY = "screen_library"
        const val SCREEN_SEARCH = "screen_search"
        const val SCREEN_SETTINGS = "screen_settings"
        const val SCREEN_IMAGE = "screen_image"
        const val SCREEN_WATCH = "screen_watch"
        const val SCREEN_VOLATILITY = "screen_volatility"
        const val SCREEN_REPORT = "screen_report"
        const val SCREEN_MORE = "screen_more"
    }

}