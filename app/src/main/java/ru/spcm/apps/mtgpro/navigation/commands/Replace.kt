package ru.spcm.apps.mtgpro.navigation.commands


/**
 * Replaces the current screen.
 */
class Replace
/**
 * Creates a [Replace] navigation command.
 *
 * @param screenKey      screen key
 * @param transitionData initial data
 */
(val screenKey: String, val transitionData: Any?) : Command