package ru.spcm.apps.mtgpro.navigation

import ru.spcm.apps.mtgpro.navigation.commands.Command
import java.util.*


/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 */
internal class CommandBuffer : NavigatorHolder {
    private var navigator: Navigator? = null
    private val pendingCommands: Queue<Array<Command?>> = LinkedList()

    override fun setNavigator(navigator: Navigator?) {
        this.navigator = navigator
        while (!pendingCommands.isEmpty()) {
            if (navigator != null) {
                executeCommands(pendingCommands.poll())
            } else
                break
        }
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    /**
     * Passes `commands` to the [Navigator] if it available.
     * Else puts it to the pending commands queue to pass it later.
     * @param commands navigation command array
     */
    fun executeCommands(commands: Array<Command?>) {
        navigator?.applyCommands(commands) ?: pendingCommands.add(commands)
    }
}