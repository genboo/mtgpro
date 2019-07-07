package ru.spcm.apps.mtgpro.navigation.android

import ru.spcm.apps.mtgpro.navigation.commands.BackTo
import ru.spcm.apps.mtgpro.navigation.commands.Command
import ru.spcm.apps.mtgpro.navigation.commands.Replace
import ru.spcm.apps.mtgpro.navigation.commands.Forward
import ru.spcm.apps.mtgpro.navigation.commands.Back
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.spcm.apps.mtgpro.navigation.Navigator
import java.util.*
import ru.spcm.apps.mtgpro.navigation.commands.SystemMessage


/**
 * [Navigator] implementation based on the support fragments.
 *
 *
 * [BackTo] navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override [.backToUnexisting] method.
 *
 *
 *
 * [Back] command will call [.exit] method if current screen is the root.
 *
 */
abstract class SupportFragmentNavigator
/**
 * Creates SupportFragmentNavigator.
 *
 * @param fragmentManager support fragment manager
 * @param containerId     id of the fragments container layout
 */
(private val fragmentManager: FragmentManager, private val containerId: Int) : Navigator {
    protected var localStackCopy: LinkedList<String>? = null

    /**
     * Override this method to setup custom fragment transaction animation.
     *
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container
     * (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    open fun setupFragmentTransactionAnimation(command: Command,
                                               currentFragment: Fragment?,
                                               nextFragment: Fragment?,
                                               fragmentTransaction: FragmentTransaction) {

        //not used
    }

    override fun applyCommands(commands: Array<Command?>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            applyCommand(command)
        }
    }

    private fun copyStackToLocal() {
        localStackCopy = LinkedList()

        val stackSize = fragmentManager.backStackEntryCount
        for (i in 0 until stackSize) {
            localStackCopy?.add(fragmentManager.getBackStackEntryAt(i).name ?: "")
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected fun applyCommand(command: Command?) {
        if (command is Forward) {
            forward(command)
        } else if (command is Back) {
            back()
        } else if (command is Replace) {
            replace(command)
        } else if (command is BackTo) {
            backTo(command)
        } else if (command is SystemMessage) {
            showSystemMessage(command.message)
        }
    }

    /**
     * Performs [Forward] command transition
     */
    protected fun forward(command: Forward) {
        val fragment = createFragment(command.screenKey, command.transitionData)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransactionAnimation(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        )

        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(command.screenKey)
                .commit()
        localStackCopy?.add(command.screenKey)
    }

    /**
     * Performs [Back] command transition
     */
    protected fun back() {
        if (!localStackCopy.isNullOrEmpty()) {
            fragmentManager.popBackStack()
            localStackCopy?.pop()
        } else {
            exit()
        }
    }

    /**
     * Performs [Replace] command transition
     */
    protected fun replace(command: Replace) {
        val fragment = createFragment(command.screenKey, command.transitionData)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        if (!localStackCopy.isNullOrEmpty()) {
            fragmentManager.popBackStack()
            localStackCopy?.pop()

            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            )

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(command.screenKey)
                    .commit()
            localStackCopy?.add(command.screenKey)

        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            )

            fragmentTransaction
                    .replace(containerId, fragment)
                    .commit()
        }
    }

    /**
     * Performs [BackTo] command transition
     */
    protected fun backTo(command: BackTo) {
        val key = command.screenKey

        if (key == null) {
            backToRoot()

        } else {
            val index = localStackCopy?.indexOf(key) ?: 0
            val size = localStackCopy?.size ?: 0

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy?.pop()
                }
                fragmentManager.popBackStack(key, 0)
            } else {
                backToUnexisting(command.screenKey)
            }
        }
    }

    private fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy?.clear()
    }

    /**
     * Creates Fragment matching `screenKey`.
     *
     * @param screenKey screen key
     * @param data      initialization data
     * @return instantiated fragment for the passed screen key
     */
    protected abstract fun createFragment(screenKey: String, data: Any?): Fragment?

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract fun showSystemMessage(message: String)

    /**
     * Called when we try to back from the root.
     */
    protected abstract fun exit()

    /**
     * Called when we tried to back to some specific screen (via [BackTo] command),
     * but didn't found it.
     * @param screenKey screen key
     */
    protected fun backToUnexisting(screenKey: String?) {
        backToRoot()
    }

    /**
     * Called if we can't create a screen.
     */
    protected fun unknownScreen(command: Command) {
        throw RuntimeException("Can't create a screen for passed screenKey.")
    }
}