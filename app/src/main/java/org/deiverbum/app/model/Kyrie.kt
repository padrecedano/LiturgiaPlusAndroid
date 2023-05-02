package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import java.util.*

class Kyrie {
    @SerializedName("introduccion")
    @Expose
    private var introduccion: String? = null

    @SerializedName("texto")
    @Expose
    private var texto: String? = null

    @SerializedName("conclusion")
    @Expose
    private var conclusion: String? = null
    val tipo: String? = null
    private var kyrieType = 0
    private val introduccionForRead: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            ssb.append(Utils.fromHtml("<p>EXAMEN DE CONCIENCIA.</p>"))
            val introArray =
                introduccion!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (introArray.size == 3) {
                ssb.append(Utils.fromHtml("<p>" + introArray[1] + "</p>"))
            } else {
                ssb.append(introduccion)
            }
            return ssb
        }

    private fun getIntroduccion(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.formatTitle(Constants.TITLE_SOUL_SEARCHING))
        ssb.append(Utils.LS2)
        val introArray =
            introduccion!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (introArray.size == 3) {
            ssb.append(Utils.toSmallSizeRed(introArray[0]))
            ssb.append(Utils.LS2)
            ssb.append(introArray[1])
            ssb.append(Utils.LS2)
            ssb.append(Utils.toSmallSizeRed(introArray[2]))
        } else {
            ssb.append(introduccion)
        }
        return ssb
    }

    @Suppress("unused")
    fun setIntroduccion(introduccion: String?) {
        this.introduccion = introduccion
    }

    private fun getTexto(): SpannableStringBuilder {
        kyrieType = Random().nextInt(3)
        return getKyrie(kyrieType)
    }

    @Suppress("unused")
    fun setTexto(texto: String?) {
        this.texto = texto
    }

    private val conclusionForRead: String
        get() = "El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna. Amén."

    private fun getConclusion(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toSmallSizeRed("Pueden usarse otras invocaciones penitenciales."))
        ssb.append(Utils.LS)
        ssb.append(Utils.toSmallSizeRed("Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos:"))
        ssb.append(Utils.LS2)
        ssb.append(Utils.toRed("V. "))
        ssb.append("El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.")
        ssb.append(Utils.LS2)
        ssb.append(Utils.toRed("R. "))
        ssb.append("Amén.")
        return ssb
    }

    @Suppress("unused")
    fun setConclusion(conclusion: String?) {
        this.conclusion = conclusion
    }

    private val textoForRead: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            when (kyrieType) {
                0 -> {
                    val text = "<p>Yo confieso ante Dios todopoderoso <br />" +
                            "y ante vosotros, hermanos <br />" +
                            "que he pecado mucho <br />" +
                            "de pensamiento, palabra, obra y omisión: <br />" +
                            "por mi culpa, por mi culpa, por mi gran culpa. <br /><br />" +
                            "Por eso ruego a santa María, siempre Virgen, <br />" +
                            "a los ángeles, a los santos y a vosotros, hermanos, <br />" +
                            "que intercedáis por mí ante Dios, nuestro Señor.</p>"
                    ssb.append(Utils.fromHtml(text))
                }
                1 -> {
                    ssb.append(Utils.fromHtml("<p>Señor, ten misericordia de nosotros.</p>"))
                    ssb.append(Utils.fromHtml("<p>Porque hemos pecado contra ti.</p>"))
                    ssb.append(Utils.fromHtml("<p>Muéstranos, Señor, tu misericordia.</p>"))
                    ssb.append(Utils.fromHtml("<p>Y danos tu salvación.</p>"))
                }
                2 -> {
                    ssb.append(Utils.fromHtml("<p>Tú que has sido enviado a sanar los corazones afligidos: Señor, ten piedad.</p>"))
                    ssb.append(Utils.fromHtml("<p>Señor, ten piedad.</p>"))
                    ssb.append(Utils.fromHtml("<p>Tú que has venido a llamar a los pecadores: Cristo, ten piedad.</p>"))
                    ssb.append(Utils.fromHtml("<p>Cristo, ten piedad.</p>"))
                    ssb.append(Utils.fromHtml("<p>Tú que estás sentado a la derecha del Padre para interceder por nosotros: Señor, ten piedad.</p>"))
                    ssb.append(Utils.fromHtml("<p>Señor, ten piedad.</p>"))
                    ssb.append("")
                }
                else -> ssb.append("")
            }
            return ssb
        }
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(getIntroduccion())
            sb.append(Utils.LS2)
            sb.append(getTexto())
            sb.append(Utils.LS2)
            sb.append(getConclusion())
            return sb
        }
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(introduccionForRead)
            sb.append(textoForRead)
            sb.append(conclusionForRead)
            return sb
        }

    companion object {
        /**
         * Este método obtiene el texto del Kyrie
         *
         * @param type El tipo de celebración.
         * @return El texto completo.
         * @since 2022.2
         */
        fun getKyrie(type: Int): SpannableStringBuilder {
            val ssb = SpannableStringBuilder()
            when (type) {
                0 -> {
                    val text = "Yo confieso ante Dios todopoderoso " + Utils.LS +
                            "y ante vosotros, hermanos " + Utils.LS +
                            "que he pecado mucho" + Utils.LS +
                            "de pensamiento, palabra, obra y omisión:" + Utils.LS +
                            "por mi culpa, por mi culpa, por mi gran culpa." + Utils.LS2 +
                            "Por eso ruego a santa María, siempre Virgen," + Utils.LS +
                            "a los ángeles, a los santos y a vosotros, hermanos," + Utils.LS +
                            "que intercedáis por mí ante Dios, nuestro Señor."
                    ssb.append(text)
                }
                1 -> {
                    ssb.append(Utils.toRed("V."))
                    ssb.append("Señor, ten misericordia de nosotros.")
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append("Porque hemos pecado contra ti.")
                    ssb.append(Utils.LS2)
                    ssb.append(Utils.toRed("V."))
                    ssb.append("Muéstranos, Señor, tu misericordia.")
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append("Y danos tu salvación.")
                }
                2 -> {
                    ssb.append(Utils.toRed("V. "))
                    ssb.append("Tú que has sido enviado a sanar los corazones afligidos: Señor, ten piedad.")
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append("Señor, ten piedad.")
                    ssb.append(Utils.LS2)
                    ssb.append(Utils.toRed("V. "))
                    ssb.append("Tú que has venido a llamar a los pecadores: Cristo, ten piedad.")
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append("Cristo, ten piedad.")
                    ssb.append(Utils.LS2)
                    ssb.append(Utils.toRed("V. "))
                    ssb.append("Tú que estás sentado a la derecha del Pater para interceder por nosotros: Señor, ten piedad.")
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append("Señor, ten piedad.")
                    ssb.append("")
                }
                else -> ssb.append("")
            }
            return ssb
        }
    }
}