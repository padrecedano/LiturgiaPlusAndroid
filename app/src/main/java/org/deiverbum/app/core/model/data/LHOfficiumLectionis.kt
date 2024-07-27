package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.TITLE_TEDEUM
import org.deiverbum.app.util.LiturgyHelper.Companion.R
import org.deiverbum.app.util.LiturgyHelper.Companion.V
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
open class LHOfficiumLectionis(
    open var lectioPrior: MutableList<LHOfficiumLectioPrior>,
    open var lectioAltera: MutableList<LHOfficiumLectioAltera>,
    open var responsorium: String = "",
    open var hasTeDeum: Boolean = false
) {

    @Json(ignore = true)
    @get:Ignore
    val sectionTitle: String = Constants.TITLE_OFFICE_OF_READING

    @Json(ignore = true)
    @get:Ignore
    private val responsorioForRead: String
        get() {
            val r: String = if (responsorium.contains("|")) {
                responsorium.replace("\\|".toRegex(), "")
            } else {
                responsorium
            }
            return Utils.pointAtEnd(r)
        }

    private fun getComposableIntro(rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            if (responsorium.contains("|")) {
                val textParts = responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (textParts.size == 2) {
                    append(Utils.toRedCompose(V, rubricColor = rubricColor))
                    append(textParts[0])
                    append(Utils.LS)
                    append(Utils.toRedCompose(R, rubricColor = rubricColor))
                    append(textParts[1])
                } else {
                    append(responsorium)
                }
            } else {
                append(responsorium)
            }
        }
    }
    @Json(ignore = true)
    @get:Ignore
    private val responsorioSpan: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            if (responsorium.contains("|")) {
                val textParts = responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (textParts.size == 2) {
                    ssb.append(Utils.toRed("V. "))
                    ssb.append(textParts[0])
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append(textParts[1])
                } else {
                    ssb.append(responsorium)
                }
            } else {
                ssb.append(responsorium)
            }
            return ssb
        }

    @Json(ignore = true)
    @get:Ignore
    open val header: SpannableStringBuilder?
        get() = Utils.formatSubTitle(
            Utils.toLower(
                Constants.TITLE_OFFICE_OF_READING
            )
        )

    @Json(ignore = true)
    @get:Ignore
    open val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFFICE_OF_READING)

    open fun getAll(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(header)
        sb.append(Utils.LS2)
        sb.append(responsorioSpan)
        sb.append(Utils.LS2)
        sb.append(getAllBiblica(calendarTime))
        sb.append(getAllPatristica(calendarTime))
        return sb
    }

    @Composable
    fun getComposable(userData: UserDataDynamic): AnnotatedString {
        val rubricColor = getRubricColor(userData = userData)
        /*SectionTitle(
            text = Constants.TITLE_OFFICE_OF_READING.lowercase(),
            level = 1
        ).getComposable()*/
        return buildAnnotatedString {
            append(getComposableIntro(rubricColor))
            append(Utils.LS2)
            for (oneBiblica in lectioPrior) {
                append(oneBiblica.getComposable(userData))
            }
            for (theModel in lectioAltera) {
                append(theModel.getComposable(userData))
            }
            if (hasTeDeum) {
                ContentTitle(
                    text = TITLE_TEDEUM.uppercase(),
                    level = 2,
                    userData = userData
                ).getComposable()
                append(TeDeum().getComposable(userData = userData))
            }
        }
    }
    @Json(ignore = true)
    @get:Ignore
    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(responsorioSpan)
            sb.append(Utils.LS2)
            sb.append(allBiblicaForView)
            sb.append(allPatristicaForView)
            if (hasTeDeum) {
                sb.append(TeDeum().all)
                sb.append(Utils.LS2)
            }
            return sb
        }

    @Json(ignore = true)
    @get:Ignore
    open val allBiblicaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (oneBiblica in lectioPrior) {
                //oneBiblica?.responsorioLargo?.all
                sb.append(oneBiblica.getAll())
            }
            return sb
        }

    open fun getAllBiblica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (oneBiblica in lectioPrior) {
            //oneBiblica?.responsorioLargo?.normalizeByTime(calendarTime)
            sb.append(oneBiblica.getAll())
        }
        return sb
    }

    @Json(ignore = true)
    @get:Ignore
    open val allBiblicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (oneBiblica in lectioPrior) {
                sb.append(oneBiblica.getAllForRead())
            }
            return sb
        }

    @Json(ignore = true)
    @get:Ignore
    private val allPatristicaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (theModel in lectioAltera) {
                sb.append(theModel.all)
            }
            return sb
        }

    private fun getAllPatristica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (theModel in lectioAltera) {
            theModel.responsorium?.normalizeByTime(calendarTime)
            sb.append(theModel.all)
        }
        return sb
    }

    @Json(ignore = true)
    @get:Ignore
    private val allPatristicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (theModel in lectioAltera) {
                sb.append(theModel.allForRead)
            }
            return sb
        }

    @Json(ignore = true)
    @get:Ignore
    open val allForRead: String
        get() = headerForRead +
                responsorioForRead +
                allBiblicaForRead +
                allPatristicaForRead

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        for (oneBiblica in lectioPrior) {
            oneBiblica.responsorium.normalizeByTime(calendarTime)
        }
        responsorium = Utils.replaceByTime(responsorium, calendarTime)
    }
}