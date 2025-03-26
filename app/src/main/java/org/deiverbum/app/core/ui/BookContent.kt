package org.deiverbum.app.core.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import org.deiverbum.app.core.designsystem.component.textBold
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.book.Book
import org.deiverbum.app.core.model.book.Content
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.NEWBIS
import org.deiverbum.app.util.Constants.TABTRI
import org.deiverbum.app.util.Utils
import java.util.Locale

/**
 * Crea el contenido proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
@Composable
fun bookRender(
    data: Book,
    level: Int,
    userData: UserData,
    rubricColor: Color
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    typography.bodyLarge.fontSize
    val uriHandler = LocalUriHandler.current
    val text = buildAnnotatedString {
        if (data.bookType == 2 || data.bookType == 3) {
            for (c in data.chapters!!) {
                append(
                    contentByType(
                        data = c.content!!,
                        userData = userData,
                        style = bodyStyle,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler
                    )
                )

            }
        } else {
            append(
                contentTitle(
                    data = data.title,
                    level = 1,
                    userData = userData,
                    uppercase = false
                )
            )
            append(NEWBIS)
            if (data.bookType == 20 || data.bookType == 21) {
                append(
                    "Fecha efectiva: ${
                        textBold(
                            Utils.formatDate(
                                data.date,
                                "yyyyMMdd",
                                "d '-' MMMM yyyy"
                            )
                        )
                    }"
                )
                append(NEWBIS)

            }

            if (data.intro != null) {
                append(
                    contentByType(
                        data = data.intro!!.content!!,
                        userData = userData,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler,
                        style = bodyStyle
                    )
                )

            }


            for (c in data.chapters!!) {
                if (data.bookType == 20) {
                    append(
                        contentTitle(
                            data = "${c.id}. ${c.title!!}",
                            level = 2,
                            userData = userData,
                            uppercase = false
                        )
                    )
                    append(NEWBIS)
                }
                if (data.bookType == 21) {
                    append("\t\t")
                    append(textRubric("${c.id}.- "))
                }
                append(
                    contentByType(
                        data = c.content!!,
                        userData = userData,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler,
                        style = bodyStyle
                    )
                )

            }
        }
        //append(contentSpace(10))
    }
    Text(text = text, style = bodyStyle)
    return text
}

//@Composable
@Composable
fun contentByType(
    data: List<Content>,
    userData: UserData,
    rubricColor: Color,
    uriHandler: UriHandler,
    style: TextStyle
): AnnotatedString {
    val text = buildAnnotatedString {
        for (content in data) {
            when (content.type) {
                10 -> {
                    append(TABTRI)
                    append(textBold(content.item!!))
                    append(" ")
                    append(getTextForView(content, userData, rubricColor))
                }

                2 -> {
                    append("\n")
                    append(contentTitle(content.title!!, 2, userData, false))
                    append(NEWBIS)
                    append(getTextForView(content, userData, rubricColor))
                }

                3 -> {
                    append(getTextForView(content, userData, rubricColor))
                }

                4 -> {
                    append(contentTitle(content.title!!, 3, userData, false))
                }

                5 -> {
                    append(contentTitle(content.title!!, 4, userData, false))
                }

                11 -> {
                    append(getTextForView(content, userData, rubricColor))
                }

                12 -> {
                    append(getTextForView(content, userData, rubricColor))
                }

                13 -> {
                    append(getNumberedList(content.text!!))
                }

                20 -> {
                    append(contentTitle(content.title!!, 2, userData, false))
                    append(NEWBIS)
                    append(getTextForView(content, userData, rubricColor))
                }

                21 -> {
                    //append("\n\n*****\n\n")
                }

                30 -> {
                    val link =
                        LinkAnnotation.Url(
                            content.text?.get(0)!!,
                            TextLinkStyles(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = rubricColor
                                )
                            )
                        ) {
                            val url = (it as LinkAnnotation.Url).url
                            uriHandler.openUri(url)
                        }
                    withLink(link) { append(content.item) }
                }

                31 -> {
                    append("\t\t\t\t")
                    append("- ")
                    val link =
                        LinkAnnotation.Url(
                            content.text?.get(0)!!,
                            TextLinkStyles(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = rubricColor
                                )
                            )
                        ) {
                            val url = (it as LinkAnnotation.Url).url
                            uriHandler.openUri(url)
                        }
                    withLink(link) { append(content.item) }
                    append("\n")
                }

                32 -> {
                    if (!content.title.isNullOrEmpty()) {
                        append(content.title?.let {
                            contentTitle(
                                it,
                                2,
                                userData,
                                false
                            )
                        })
                    }
                    val bookLink = content.link
                    if (!bookLink!!.previous.isNullOrEmpty()) {
                        append(bookLink.previous?.let { AnnotatedString.fromHtml(it) })
                        append(" ")
                    }
                    val link =
                        LinkAnnotation.Url(
                            bookLink.url,
                            TextLinkStyles(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = rubricColor
                                )
                            )
                        ) {
                            val url = (it as LinkAnnotation.Url).url
                            uriHandler.openUri(url)
                        }
                    withLink(link) { append(bookLink.textus) }
                    append(" ")
                    if (!bookLink.posterioris.isNullOrEmpty()) {
                        append(bookLink.posterioris?.let { AnnotatedString.fromHtml(it) })
                    }
                    if (bookLink.haveBreak) {
                        append(NEWBIS)
                    }
                }

                33 -> {
                    append("\t\t\t\t - ")

                    if (!content.title.isNullOrEmpty()) {
                        append(content.title?.let {
                            contentTitle(
                                it,
                                2,
                                userData,
                                false
                            )
                        })
                    }
                    val bookLink = content.link
                    if (!bookLink!!.previous.isNullOrEmpty()) {
                        append(bookLink.previous?.let { AnnotatedString.fromHtml(it) })
                        append(" ")
                    }
                    val link =
                        LinkAnnotation.Url(
                            bookLink.url,
                            TextLinkStyles(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = rubricColor
                                )
                            )
                        ) {
                            val url = (it as LinkAnnotation.Url).url
                            uriHandler.openUri(url)
                        }
                    withLink(link) { append(bookLink.textus) }
                    append("\n")
                    if (!bookLink.posterioris.isNullOrEmpty()) {
                        append(bookLink.posterioris?.let { AnnotatedString.fromHtml(it) })
                    }
                    if (bookLink.haveBreak) {
                        append("NEWBIS")
                    }
                }


                else -> {
                    append(getTextForView(content, userData, rubricColor))
                }
            }
        }
    }
    //Text(text=text, style=style)
    return text
}

@Composable
fun bookContent(
    data: List<Content>?,
    userData: UserData,
    rubricColor: Color
): AnnotatedString {
    val sb = buildAnnotatedString {
        //append(contentTitle("CCC", 1, userData, rubricColor))

        for (s in data!!) {
            when (s.type) {
                10 -> {
                    val txt = Utils.fromHtml(
                        String.format(
                            Locale("es"), "%s<b>%s</b> %s",
                            Constants.NBSP_4, s,
                            getTextForView(s, userData, rubricColor)
                        )
                    )
                    append(txt)
                    append(Utils.LS2)
                }

                2 -> {
                    append(contentTitle(s.title!!, 3, userData))
                    append(getTextForView(s, userData, rubricColor))
                }

                3 -> {
                    append(contentTitle(s.title!!, 4, userData))
                    append(getTextForView(s, userData, rubricColor))
                }

                4 -> {
                    append(contentTitle(s.title!!, 3, userData))
                    append(getTextForView(s, userData, rubricColor))
                }

                5 -> {
                    append(contentTitle(s.title!!, 4, userData))
                    append(getTextForView(s, userData, rubricColor))
                }

                11 -> {
                    append(getTextForView(s, userData, rubricColor))
                }

                12 -> {
                    append(getTextForView(s, userData, rubricColor))
                }

                13 -> {
                    append(getNumberedList(s.text!!))
                }

                20 -> {
                    append(contentTitle(s.title!!, 2, userData, false))
                    append(getTextForView(s, userData, rubricColor))
                }

                else -> {
                    append(getTextForView(s, userData, rubricColor))
                }
            }
        }
    }
    return sb
}

fun getTextForView(data: Content, userData: UserData, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        for (s in data.text!!) {
            if (data.type == 11) {
                append("\t\t\t\t")
                append("- ")
            }
            if (data.type < 4) {
                append("\t\t")
            }
            if (data.type == 20) {
                //append(contenTitle(s.))
                //append(Utils.toH3Red(s))
                //append(Utils.LS2)
            }
            //append(textSpan(AnnotatedString.fromHtml(s),userData))
            append(AnnotatedString.fromHtml(s))
            append(contentSpace(10))
        }
    }//return sb
}

fun getNumberedList(data: List<String>): AnnotatedString {
    var i = 1
    return buildAnnotatedString {
        for (s in data) {
            append("\t\t ${i}. ${AnnotatedString.fromHtml(s)}${NEWBIS}")
            i++
        }
    }
}