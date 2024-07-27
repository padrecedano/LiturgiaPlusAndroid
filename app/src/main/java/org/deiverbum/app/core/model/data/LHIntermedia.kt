package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los tres horas intermedias del salterio:
 * **`Tercia`**, **`Sexta`** y **`Nona`** en la capa de datos externa.
 *
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property horaId Un entero para identificar la hora:
 *           `3` para `Tercia`, `4` para `Sexta` y `5` para `Nona`.
 */
@JsonClass(generateAdapter = true)
data class LHIntermedia(
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var oratio: Oratio,
    var typusID: Int = 0,
    //@Json(ignore = true)
    override var typus: String = "intermedia"
) : Breviarium(typus) {
    val tituloHora: String
        get() = when (typusID) {
            3 -> Constants.TITLE_TERCIA
            4 -> Constants.TITLE_SEXTA
            5 -> Constants.TITLE_NONA
            else -> ""
        }
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(tituloHora)
    val tituloHoraForView: SpannableStringBuilder
        get() = Utils.toH1Red(tituloHora)

    @Composable
    override fun allForView(calendarTime: Int, userData: UserDataDynamic) {
        val content = IntroitusNew.createIntroitus()
        val rubricColor = getRubricColor(userData = userData)
        val introitus = Introitus()
        ContentTitle(
            text = Constants.TITLE_INITIAL_INVOCATION.uppercase(),
            level = 2,
            userData = userData
        ).getComposable()
        //Text(text = introitus.composableDeusInAdiutorium(rubricColor))

//append(hymnus.getComposable(userData = userData))
        Text(text = hymnus.getComposable(userData = userData))

//VerseContent(text = hymnus.hymnus, userData =userData )
//            ContentHead(text= Constants.TITLE_RESPONSORY,level=2,userData=userData).getComposable()
//            VerseContent(text = oratio.oratio, userData =userData )
        Text(text = psalmodia.getComposable(hourIndex, calendarTime, userData))

        //ContentTitle(text= Constants.TITLE_SHORT_READING.uppercase(),level=2,userData=userData).getComposable()
        Text(text = lectioBrevis.getComposable(hourIndex, userData))


        ContentTitle(
            text = Constants.TITLE_PRAYER.uppercase(),
            level = 2,
            userData = userData
        ).getComposable()
        Text(text = oratio.getComposable(userData))

        val data = listOf(Pair(content.placeHolder, SpanStyle(color = Color.Red)), content.content)
        buildAnnotatedString {
            //append(annotateRecursivelyNew())
            /*append(annotateRecursively(
                    listOf(Pair(content.placeHolder, SpanStyle(color = Color.Red))),
                    content.content
                )*/
            //)
            //append(Introitus.stuffDone(a = Introitus(), userData = userData))
//        ContentHead(text ="Level 1", level =1).getComposable()
//            ContentHead(text ="Level 2", level =2, userData =userData).getComposable()
            //ContentHead(text= Constants.TITLE_HYMN,level=2,userData=userData).getComposable()
//append(hymnus.getComposable(userData = userData))
            //Text(text=hymnus.getComposable(userData = userData))

//VerseContent(text = hymnus.hymnus, userData =userData )
//            ContentHead(text= Constants.TITLE_RESPONSORY,level=2,userData=userData).getComposable()
//            VerseContent(text = oratio.oratio, userData =userData )
            //Text(text=psalmodia.getComposable(hourIndex, calendarTime,userData))
            //ContentTitle(text= Constants.TITLE_SHORT_READING.uppercase(),level=2,userData=userData).getComposable()

            //Text(text=lectioBrevis.getComposable(hourIndex,userData))


            //hymnus.ViewContent(userData=userData)
        }
        /*val annotatedString = buildAnnotatedString {

            //append(initialInvocation)
        append(stuffDone(Introitus(),userData))

        //h3Rubric(text = liturgia?.nomen!!, userData = userData)
        //TextBody(text = fecha, useLineBreak =true )
        //TextBody(text = liturgia?.tempus?.externus!!, useLineBreak =true )

    }*/
        //return annotatedString
    }

    override fun forView(calendarTime: Int): SpannableStringBuilder {
        //this.typeID = typeID
        val sb = SpannableStringBuilder(tituloHoraForView)
        sb.append(Utils.LS2)

        //super.forView()
        //sb.append(this.li)
        //sb.append(parent.forView())
        try {
            lectioBrevis.normalizeByTime(calendarTime)
            //sb.append(tituloHoraForView)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            //sb.append(Utils.LS)
            sb.append(hymnus.all)
            sb.append(Utils.LS2)
            //sb.append(Utils.LS)
            sb.append(psalmodia.getAllForView(hourIndex, calendarTime))
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllWithHourCheck(typusID))
            sb.append(Utils.LS)
            sb.append(oratio.all)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(RitusConclusionis.viewBenedicamusDomino)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //sb.append(today.getAllForRead());
    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(tituloHoraForRead)
            sb.append(initialInvocationForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead(hourIndex))
            sb.append(lectioBrevis.getAllForRead())
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readBenedicamusDomino)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    /**
     * Devuelve el índice de la hora para fines de LHPsalmody
     * por ejemplo, para determinar la antífona única en los
     * tiempos litúrgicos en que ésta aplica
     *
     * @return Un entero con el índice 0
     * @since 2022.1
     */
    val hourIndex: Int
        get() = when (typusID) {
            4 -> 1
            5 -> 2
            3 -> 0
            else -> 0
        }
}