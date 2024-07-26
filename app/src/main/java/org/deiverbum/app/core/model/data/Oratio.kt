package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.room.ColumnInfo
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Oratio(
    var prayerID: Int = 0,
    @ColumnInfo(name = "prayer")
    var oratio: String,
    var order: Int = 0
) {

    /**
     * El texto de la oración.
     * El setter verifica si la misma contiene el símbolo *
     * para extraerlo de la cadena. Este símbolo permanece en aquellas
     * oraciones que no han sido pasadas a la nueva versión del Misal Romano (año 2016).
     */
    init {
        if (oratio.contains("*")) {
            oratio.replace("*", "")
        }
    }

    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PRAYER)

    @Composable
    fun getComposable(userData: UserDataDynamic?): AnnotatedString {
        //ContentTitle(text= Constants.TITLE_SHORT_READING.uppercase(),level=2,userData=userData).getComposable()
        ContentTitle(
            text = Constants.TITLE_PRAYER.uppercase(),
            level = 2,
            userData = userData
        ).getComposable()
        return buildAnnotatedString {
            append(Utils.fromHtml(oratio))
        }
    }
    val all: Spanned
        get() {
            val sb = SpannableStringBuilder("")
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(oratio))
            return sb
        }
    val allForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PRAYER) +
                Utils.fromHtml(oratio)
}