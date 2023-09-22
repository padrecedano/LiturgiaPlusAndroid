package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class PadreNuestro {

    companion object {
        const val texto = "Padre nuestro,~¦que estás en el cielo,~¦santificado sea tu Nombre;~¦" +
                "venga a nosotros tu reino;~¦hágase tu voluntad~¦en la tierra como en el cielo.~¦" +
                "Danos hoy nuestro pan de cada día;~¦perdona nuestras ofensas,~¦" +
                "como también nosotros perdonamos a los que nos ofenden;~¦" +
                "no nos dejes caer en la tentación,~¦y líbranos del mal. Amén."
        val all: SpannableStringBuilder
            get() {
                val sb = SpannableStringBuilder("")
                sb.append(
                    Utils.formatTitle(
                        Constants.TITLE_PATER_NOSTER
                    )
                )
                sb.append(Utils.LS2)
                sb.append(Utils.fromHtml(texto))
                return sb
            }
        val allForRead: SpannableStringBuilder
            get() {
                return SpannableStringBuilder(Utils.fromHtml(texto))
            }
    }
}