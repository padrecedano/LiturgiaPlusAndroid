package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class LHHymn(
    @ColumnInfo(name = "hymn")
    var hymnus: String = ""
) {
    var hymnID: Int = 0

    @Json(ignore = true)
    private val textoSpan: Spanned
        get() = Utils.fromHtml(hymnus)
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_HYMN)

    @Json(ignore = true)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_HYMN)

    @Composable
    fun getComposable(userData: UserDataDynamic): AnnotatedString {
        //val rubricColor= getRubricColor(userData = userData)
        ContentTitle(
            text = Constants.TITLE_HYMN.uppercase(),
            level = 2,
            userData = userData
        ).getComposable()
        val asb = AnnotatedString.Builder()
        asb.append(textoSpan.toString())
        return buildAnnotatedString {
            append(Utils.fromHtml(hymnus))
        }
    }


    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            return sb
        }
    val allForRead: String
        get() = headerForRead +
                textoSpan
}

@Composable
fun LHHymn.ViewContent(userData: UserDataDynamic) {
    ContentHead(text = Constants.TITLE_HYMN, level = 2, userData = userData).getComposable()

    val lstValues: List<String> = hymnus.split("§").map { it -> it.trim() }
    lstValues.forEach { it ->
        Spacer(modifier = Modifier.height(16.dp))
        it.split("~").map { it -> it.trim() }.forEach { verse ->
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                        append(verse)

                    }
                }
            )

            //println(verse)}
            //println(it);
        }

        //println(lst)
    }


    /*Text(
        //hymnID = hymnID,
        text = hymnus
    )*/
}