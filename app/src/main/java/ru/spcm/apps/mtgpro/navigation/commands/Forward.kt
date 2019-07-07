package ru.spcm.apps.mtgpro.navigation.commands

/**
 * Opens new screen.
 */
class Forward
/**
 * Creates a [Forward] navigation command.
 *
 * @param screenKey      screen key
 * @param transitionData initial data
 */
(val screenKey: String, val transitionData: Any?) : Command