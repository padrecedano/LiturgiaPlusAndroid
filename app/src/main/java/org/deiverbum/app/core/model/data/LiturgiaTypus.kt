package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 * Esta clase sirve para manejar los diferentes tipos de celebraciones litúrgicas.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 *
 */

abstract class LiturgiaTypus
    (open val typus: String) {
    open fun getHeaders(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun forRead(): StringBuilder {
        return StringBuilder("")
    }

}

/*
sealed interface LiturgiaTypus {
    var typus:String
    var tempore:LiturgyTime

    fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

    fun forRead(): StringBuilder {
        return StringBuilder("")
    }
/*
    object Unknown : LiturgiaTypus {
        override var typus: String
            get() = ""
            set(value) {}
    }*/

}*/