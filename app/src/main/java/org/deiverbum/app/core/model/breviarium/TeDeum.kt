package org.deiverbum.app.core.model.breviarium

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class TeDeum {
    var texto: String =
        "Señor, Dios eterno, alegres te cantamos, \na ti nuestra alabanza, \na ti, Padre del cielo, te aclama la creación. \n\nPostrados ante ti, los ángeles te adoran \ny cantan sin cesar: \n\nSanto, santo, santo es el Señor, \nDios del universo; \nllenos están el cielo y la tierra de tu gloria. \n\nA ti, Señor, te alaba el coro celestial de los apóstoles, \nla multitud de los profetas te enaltece, \ny el ejército glorioso de los mártires te aclama. \n\nA ti la Iglesia santa, \npor todos los confines extendida, \ncon júbilo te adora y canta tu grandeza: \n\nPadre, infinitamente santo, \nHijo eterno, unigénito de Dios, \nSanto Espíritu de amor y de consuelo. \n\nOh Cristo, tú eres el Rey de la gloria, \ntú el Hijo y Palabra del Padre, \ntú el Rey de toda la creación. \n\nTú, para salvar al hombre, \ntomaste la condición de esclavo \nen el seno de una virgen. \n\nTú destruiste la muerte \ny abriste a los creyentes las puertas de la gloria. \n\nTú vives ahora, \ninmortal y glorioso, en el reino del Padre. \n\nTú vendrás algún día, \ncomo juez universal. \n\nMuéstrate, pues, amigo y defensor \nde los hombres que salvaste. \n\nY recíbelos por siempre allá en tu reino, \ncon tus santos y elegidos. \n\nSalva a tu pueblo, Señor, \ny bendice a tu heredad. \n\nSé su pastor, \ny guíalos por siempre. \n\nDía tras día te bendeciremos \ny alabaremos tu nombre por siempre jamás. \n\nDígnate, Señor, \nguardarnos de pecado en este día. \n\nTen piedad de nosotros, Señor, \nten piedad de nosotros. \n\nQue tu misericordia, Señor, venga sobre nosotros, \ncomo lo esperamos de ti. \n\nA ti, Señor, me acojo, \nno quede yo nunca defraudado."

    private var isStatus: Boolean = true

    val all: Spanned
        get() {
            val sb = SpannableStringBuilder("")
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(texto))
            return sb
        }

    /**
     * @return Contenido del TeDeum
     */
    /**
     * @return Contenido del TeDeum
     */
    @get:Deprecated("desde la versión 2022.01 - Usar en su lugar {@link TeDeum#getAll()}")
    val textoSpan: Spanned
        get() {
            val sb = SpannableStringBuilder("")
            if (isStatus) {
                sb.append(header)
                sb.append(Utils.LS2)
                sb.append(Utils.fromHtml(texto))
            }
            return sb
        }
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_TEDEUM)
    val allForRead: String
        get() {
            val sb = StringBuilder()
            if (isStatus) {
                sb.append(header)
                sb.append(".")
                sb.append(Utils.fromHtml(texto))
            }
            return sb.toString()
        }
}