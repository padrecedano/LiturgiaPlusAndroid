package org.deiverbum.app.core.model.alteri

import org.deiverbum.app.util.Utils

class IntroViaCrucis {
    @Suppress("unused")
    var saludo: String? = null
    var intro: String? = null
        get() = if (field?.isNotEmpty() == true) Utils.getFormato(field!!) else ""
    var oracion: String? = null
        get() = if (field?.isNotEmpty() == true) Utils.getFormato(field!!) else ""
}