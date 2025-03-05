package org.deiverbum.app.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import org.deiverbum.app.core.designsystem.component.textBold
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.book.Book
import org.deiverbum.app.core.model.data.book.Content
import org.deiverbum.app.util.Constants
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
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)
    val fontSize = typography.bodyLarge.fontSize
    val uriHandler = LocalUriHandler.current
    return buildAnnotatedString {
        if (data.bookType == 2 || data.bookType == 3) {
            for (c in data.chapters!!) {
                append(
                    contentByType(
                        data = c.content!!,
                        userData = userData,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler
                    )
                )
            }
        } else {
            append(contentTitle(data.title, 1, userData, rubricColor, false))
            if (data.bookType == 20 || data.bookType == 21) {
                append("Fecha efectiva: ")
                append(textBold(Utils.formatDate(data.date, "yyyyMMdd", "d '-' MMMM yyyy")))
                append(contentSpace(10))
            }
            if (data.intro != null) {
                append(
                    contentByType(
                        data = data.intro!!.content!!,
                        userData = userData,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler
                    )
                )
            }
            for (c in data.chapters!!) {
                if (data.bookType == 20) {
                    append(contentTitle("${c.id}. ${c.title!!}", 2, userData, rubricColor))
                }
                if (data.bookType == 21) {
                    append("\t\t")
                    append(textRubric("${c.id}.- ", rubricColor, fontSize))
                }
                append(
                    contentByType(
                        data = c.content!!,
                        userData = userData,
                        rubricColor = rubricColor,
                        uriHandler = uriHandler
                    )
                )
            }
        }
        append(contentSpace(10))
    }
}

fun contentByType(
    data: List<Content>,
    userData: UserDataDynamic,
    rubricColor: Color,
    uriHandler: UriHandler
): AnnotatedString {
    return buildAnnotatedString {
        for (content in data) {
            when (content.type) {
                10 -> {
                    append(TABTRI)
                    append(textBold(content.item!!))
                    append(" ")
                    append(getTextForView(content, userData, rubricColor))
                }

                2 -> {
                    append(contentTitle(content.title!!, 2, userData, rubricColor, false))
                    append(getTextForView(content, userData, rubricColor))
                }

                3 -> {
                    append(contentTitle(content.title!!, 4, userData, rubricColor, false))
                    append(getTextForView(content, userData, rubricColor))
                }

                4 -> {
                    append(contentTitle(content.title!!, 3, userData, rubricColor, false))
                }

                5 -> {
                    append(contentTitle(content.title!!, 4, userData, rubricColor, false))
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
                    append(contentTitle(content.title!!, 2, userData, rubricColor, false))
                    append(getTextForView(content, userData, rubricColor))
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
                                rubricColor,
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
                        append("\n\n")
                    }
                }

                else -> {
                    append(getTextForView(content, userData, rubricColor))
                }
            }
        }
    }
}

fun bookContent(
    data: List<Content>?,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val sb = buildAnnotatedString {
        append(contentTitle("CCC", 1, userData, rubricColor))

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
                    append(contentTitle(s.title!!, 3, userData, rubricColor))
                    append(getTextForView(s, userData, rubricColor))
                }

                3 -> {
                    append(contentTitle(s.title!!, 4, userData, rubricColor))
                    append(getTextForView(s, userData, rubricColor))
                }

                4 -> {
                    append(contentTitle(s.title!!, 3, userData, rubricColor))
                    append(getTextForView(s, userData, rubricColor))
                }

                5 -> {
                    append(contentTitle(s.title!!, 4, userData, rubricColor))
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
                    append(contentTitle(s.title!!, 2, userData, rubricColor, false))
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

fun getTextForView(data: Content, userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
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
            append("\t\t ${i}. ${AnnotatedString.fromHtml(s)}\n\n")
            i++
        }
    }
}