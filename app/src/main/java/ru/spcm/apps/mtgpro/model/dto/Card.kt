package ru.spcm.apps.mtgpro.model.dto

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import ru.spcm.apps.mtgpro.model.tools.Icons

@Entity(indices = [(Index("multiverseId")), (Index("set"))])
data class Card(@PrimaryKey @NonNull var id: String) {

    var name: String = ""

    @SerializedName("multiverseid")
    var multiverseId: String? = ""

    var imageUrl: String = ""

    var text: String? = ""

    var flavor: String? = ""

    var number: String? = ""

    var numberFormatted: String = ""

    @SerializedName("setName")
    var setTitle: String = ""

    var set: String = ""

    var type: String = ""

    var manaCost: String? = ""

    var cmc: String = ""

    var rarity: String = ""

    var rulesText: String? = ""

    var count: Int = 0

    var parent: String? = ""

    @Ignore
    private val foreignNames: List<ForeignName>? = null

    @Ignore
    private val rulings: List<Rule>? = null

    @Ignore
    val supertypes: List<String>? = null

    @Ignore
    val types: List<String>? = null

    @Ignore
    val subtypes: List<String>? = null

    @Ignore
    val colors: List<String>? = null

    @Ignore
    val printings: List<String>? = null

    var nameOrigin: String? = null

    fun getSetIcon(): Int {
        return Icons.getIcon(set)
    }

    fun getSetIconColor(): Int {
        return Icons.getColor(rarity)
    }

    fun prepare() {
        if (nameOrigin == null) {
            nameOrigin = name
            prepareNumber()
            prepareImageUrl()
            prepareRules()
        }
    }

    private fun prepareNumber() {
        val num = number
        if (num == null) {
            numberFormatted = ""
            number = ""
        } else {
            numberFormatted = num
            if (num.endsWith("a") || num.endsWith("b")) {
                if (num.length == 2) {
                    numberFormatted = "00$num"
                } else if (num.length == 3) {
                    numberFormatted = "0$num"
                }
                numberFormatted = numberFormatted.replace("a", "").replace("b", "")
            } else if (num.length == 1) {
                numberFormatted = "00$number"
            } else if (num.length == 2) {
                numberFormatted = "0$num"
            }
        }
    }

    private fun prepareImageUrl() {
        //Локализованная картинка
        if (foreignNames != null) {
            for (foreignName in foreignNames) {
                if (foreignName.language == "Russian") {
                    imageUrl = foreignName.imageUrl
                    name = foreignName.name
                    break
                }
            }
        }

        if (imageUrl == null) {
            imageUrl = ""
        }
    }

    private fun prepareRules() {
        //Правила
        if (rulings != null) {
            val textRules = StringBuilder()
            for (rule in rulings) {
                textRules.append(rule.text).append("\n")
            }
            rulesText = textRules.toString()
        }
    }
}