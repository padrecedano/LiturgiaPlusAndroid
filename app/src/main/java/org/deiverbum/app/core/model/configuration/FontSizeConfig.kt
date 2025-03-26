package org.deiverbum.app.core.model.configuration

import kotlinx.serialization.Serializable

@Serializable
enum class FontSizeConfig(
    val key: String,
    val title: String,
    val fontSizeExtra: Int,
) {
    DEFAULT("14", "", 0),
    XS2("10", "", -4),
    XS("12", "", -2),
    SM("14", "", 0),
    MD("16", "", 2),
    LG("18", "", 4),
    XL("20", "", 6),
    XL2("24", "", 10),
    XL3("28", "", 14),
    XL4("32", "", 18),
    XL5("36", "", 22),
    XL6("40", "", 26),
    XL7("48", "", 34),
    XL8("56", "", 42),
    XL9("64", "", 50),
    XL10("72", "", 58),
    XL11("80", "", 66),
    XL12("96", "", 82);

    override fun toString(): String {
        return "$name - ${key}pt"
    }

    companion object {
        fun getFontPrefFromKey(key: String?): FontSizeConfig {
            return FontSizeConfig.values().find {
                it.key == key
            } ?: DEFAULT
        }
    }
}