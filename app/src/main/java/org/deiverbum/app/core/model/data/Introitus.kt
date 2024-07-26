package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.core.designsystem.component.TextSectionHead
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
//txtDomineAdAdiuvandum me
class Introitus {
    val txtDeusInAdiutorium = "Dios mío, ven en mi auxilio."
    val txtDomineAdAdiuvandum = "Señor, date prisa en socorrerme."
    val txtDomineLabia = "Señor, abre mis labios."
    val txtEtOsMeum = "Y mi boca proclamará tu alabanza."

    @Composable
    fun doStuff(userData: UserDataDynamic) = AnnotatedString(

        "aaaa"
    )

    //TextRubriccc(text = "V./ ", userData = userData )
    //Text(as1())
    //Text("Test")
    //Text("Dios mío, ven en mi auxilio.")})




    companion object {
        @Composable
        fun stuffDone(a: Introitus, userData: UserDataDynamic) = a.doStuff(userData)
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

        /*val initialInvocation: AnnotatedString
            @Composable
            get() {
                return buildAnnotatedString {

                    textSectionHead(text = Constants.TITLE_INITIAL_INVOCATION)

                    //h3Rubric(text = liturgia?.nomen!!, userData = userData)
                    //TextBody(text = fecha, useLineBreak =true )
                    //TextBody(text = liturgia?.tempus?.externus!!, useLineBreak =true )

                }

            }*/

        val introDiosMioForRead: String =
            "Dios mío, ven en mi auxilio. Señor, date prisa en socorrerme. " + LiturgyHelper.endPsalmForRead

        val initialInvocationForRead: String =
            "Invocación inicial. " + introDiosMioForRead
    }

    fun composableDeusInAdiutorium(rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            append(Utils.toRedCompose(LiturgyHelper.V, rubricColor = rubricColor))
            append(txtDeusInAdiutorium)
            append(Utils.LS)
            append(Utils.toRedCompose(LiturgyHelper.R, rubricColor = rubricColor))
            append(txtDomineAdAdiuvandum)
            append(Utils.LS2)
            append(LiturgyHelper.endPsalmForView)
        }
    }

    fun composableDomineLabia(rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            append(Utils.toRedCompose(LiturgyHelper.V, rubricColor = rubricColor))
            append(txtDomineLabia)
            append(Utils.LS)
            append(Utils.toRedCompose(LiturgyHelper.R, rubricColor = rubricColor))
            append(txtEtOsMeum)
            append(Utils.LS2)
            append(LiturgyHelper.endPsalmForView)
        }
    }

}

@Composable
fun textSectionHead(text: String, userData: UserDataDynamic): AnnotatedString {
    return buildAnnotatedString {

        TextSectionHead(text = Constants.TITLE_INITIAL_INVOCATION, userData)

        //h3Rubric(text = liturgia?.nomen!!, userData = userData)
        //TextBody(text = fecha, useLineBreak =true )
        //TextBody(text = liturgia?.tempus?.externus!!, useLineBreak =true )

    }
}




