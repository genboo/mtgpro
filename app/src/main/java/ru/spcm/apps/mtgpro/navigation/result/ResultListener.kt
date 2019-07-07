package ru.spcm.apps.mtgpro.navigation.result

interface ResultListener {

    /**
     * Received result from screen.
     *
     * @param resultData
     */
    fun onResult(resultData: Any)
}