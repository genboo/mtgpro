package ru.spcm.apps.mtgpro.tools

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Float.format(): String {
    val symbols = DecimalFormatSymbols()
    symbols.decimalSeparator = '.'
    val diffFormatter = DecimalFormat("###.##", symbols)
    return diffFormatter.format(this)
}