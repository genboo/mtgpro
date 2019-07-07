package ru.spcm.apps.mtgpro.navigation

import ru.spcm.apps.mtgpro.navigation.commands.Command


/**
 * The low-level navigation interface.
 * Navigator is the one who actually performs any transition.
 */
interface Navigator {

    /**
     * Performs transition described by the navigation command
     *
     * @param commands the navigation command array to apply per single transaction
     */
    fun applyCommands(commands: Array<Command?>)
}