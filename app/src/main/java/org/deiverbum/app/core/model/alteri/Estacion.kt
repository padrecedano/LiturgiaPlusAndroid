package org.deiverbum.app.core.model.alteri

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.biblia.TextoBiblico
import org.deiverbum.app.util.Utils

class Estacion {
    var titulo: String? = null
    var subtitulo: String? = null
    var textoBiblico: TextoBiblico? = null
    private var meditacion: Meditacion? = null
    @Suppress("unused")
    var aclamaciones: String? = null
    var r = 0
    var canto: String? = null
    fun getMeditacionSpan(respuestas: List<String?>): SpannableStringBuilder {
        val ssb = SpannableStringBuilder("")
        ssb.append(Utils.toH4Red("Meditación"))
        ssb.append(Utils.LS2)
        ssb.append(Utils.fromHtml(meditacion!!.texto!!))
        if (meditacion!!.tipo == 2) {
            ssb.append(Utils.LS)
            ssb.append(Utils.toH4Red("Oración"))
            ssb.append(Utils.LS2)
            ssb.append(Utils.fromHtml(meditacion!!.textoPost!!))
        } else {
            ssb.append(Utils.LS2)
            ssb.append(Utils.toH4Red("Aclamaciones"))
            ssb.append(Utils.LS2)
            val textParts =
                meditacion!!.textoPost?.split("\\|".toRegex())?.dropLastWhile { it.isEmpty() }
                    ?.toTypedArray()
            if (textParts != null) {
                for (part in textParts) {
                    ssb.append(
                        Utils.fromHtml(
                            Utils.getFormato(part)))
                    ssb.append(Utils.LS2)
                    ssb.append(Utils.toRed("R/. "))
                    ssb.append(respuestas[r])
                    ssb.append(Utils.LS2)
                }
            }
        }
        return ssb
    }

    val textoBiblicoSpan: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder("")
            ssb.append(textoBiblico!!.libro)
            ssb.append("     ")
            ssb.append(Utils.toRed(textoBiblico!!.ref))
            ssb.append(Utils.LS2)
            ssb.append(
                Utils.fromHtml(
                    Utils.getFormato(
                        textoBiblico!!.texto!!
                    )
                )
            )
            return ssb
        }
}