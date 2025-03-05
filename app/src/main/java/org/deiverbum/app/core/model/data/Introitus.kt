package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Saludos iniciales de la Liturgia.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */

class Introitus {
    val txtDeusInAdiutorium = "Dios mío, ven en mi auxilio."
    val txtDomineAdAdiuvandum = "Señor, date prisa en socorrerme."
    val txtDomineLabia = "Señor, abre mis labios."
    val txtEtOsMeum = "Y mi boca proclamará tu alabanza."
    val txtInNomine = "En el nombre del Padre, y del Hijo, y del Espíritu Santo."
    val txtAmen = "Amén."
    val prima = listOf(txtDomineLabia, txtEtOsMeum)
    val altera = listOf(txtDeusInAdiutorium, txtDomineAdAdiuvandum)

    companion object {
        val contentTitle: String = Constants.TITLE_INITIAL_INVOCATION
        val inNomineForView: SpannableStringBuilder
            get() {
                return SpannableStringBuilder(Utils.toRed("V/. "))
                    .append("En el nombre del Padre, y del Hijo, y del Espíritu Santo.")
                    .append(Utils.LS)
                    .append(Utils.toRed("R/. "))
                    .append("Amén.")
                    .append(Utils.LS2)
            }


        var inNomineForRead: String =
            "En el nombre del Padre, y del Hijo, y del Espíritu Santo. Amén."

        val introDiosMioForView: SpannableStringBuilder
            get() {
                return SpannableStringBuilder(Utils.toRed("V/. "))
                    .append("Dios mío, ven en mi auxilio.")
                    .append(Utils.LS)
                    .append(Utils.toRed("R/. "))
                    .append("Señor, date prisa en socorrerme.")
                    .append(Utils.LS2)
                    .append(LiturgyHelper.endPsalmForView)
            }

        val viewDomineLabiaMeaAperis: SpannableStringBuilder
            get() {
                return SpannableStringBuilder(Utils.toRed("V/. "))
                    .append("Señor, abre mis labios.")
                    .append(Utils.LS)
                    .append(Utils.toRed("R/. "))
                    .append("Y mi boca proclamará tu alabanza.")
                    .append(Utils.LS2)
                    .append(LiturgyHelper.endPsalmForView)
            }

        val readDomineLabiaMeaAperis: String =
            "Señor, abre mis labios. Y mi boca proclamará tu alabanza." +
                    LiturgyHelper.endPsalmForView


        val initialInvocationForView: SpannableStringBuilder
            get() {
                return SpannableStringBuilder(Utils.formatTitle(Constants.TITLE_INITIAL_INVOCATION))
                    .append(Utils.LS2)
                    .append(introDiosMioForView)
            }

        val introDiosMioForRead: String =
            "Dios mío, ven en mi auxilio. Señor, date prisa en socorrerme. " + LiturgyHelper.endPsalmForRead

        val initialInvocationForRead: String =
            "Invocación inicial. " + introDiosMioForRead
    }
}




