package ru.spcm.apps.mtgpro.navigation.commands


/**
 * Rolls back to the needed screen from the screens chain.
 * Behavior in the case when no needed screens found depends on an implementation of the [Navigator].
 * But the recommended behavior is to return to the root.
 */
class BackTo
/**
 * Creates a [BackTo] navigation command.
 *
 * @param screenKey screen key
 */
(val screenKey: String?) : Command