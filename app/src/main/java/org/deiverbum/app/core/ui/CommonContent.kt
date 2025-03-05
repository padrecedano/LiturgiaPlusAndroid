package org.deiverbum.app.core.ui

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.deiverbum.app.core.designsystem.component.normalStyle
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Utils

@Composable
fun ContentTitle(text: String, level: Int, rubricColor: Color) {
    val textStyle: TextStyle = when (level) {
        1 -> NiaTypography.titleLarge
        2 -> NiaTypography.titleMedium
        3 -> NiaTypography.titleSmall
        else -> NiaTypography.bodyLarge
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = text.uppercase(), style = textStyle, color = rubricColor)
    Spacer(modifier = Modifier.height(16.dp))
}

fun contentTitle(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    uppercase: Boolean = true
): AnnotatedString {
    val finalText = if (uppercase) text.uppercase() else text
    val typography = getPersonalizedTypography(userData.fontSize)
    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        4 -> typography.bodyLarge.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = fontSize,
                color = rubricColor
            )
        ) {
            append(finalText)
        }
    }
}

fun contentTitleForHomily(text: String, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(
        lineHeight = 18.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )

    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        4 -> NiaTypography.bodyLarge.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {

        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(text.uppercase())
            }
        }

        withStyle(style = paragraphStyle) {
        }
    }
}

fun contentTitleAndText(
    texts: List<String>,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)

    val paragraphStyle = ParagraphStyle(
        //lineHeight = 25.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))

        withStyle(style = paragraphStyle) {
        }
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    //fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(texts[0].uppercase())
            }
            withStyle(
                SpanStyle(
                    color = rubricColor
                )
            ) {
                append("   ")
                append(texts[1])
            }
        }
        withStyle(style = paragraphStyle) {
        }
    }
}

fun sectionTitle(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    lower: Boolean = true
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)

    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))

        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            if (lower) {
                append(text.lowercase())
            } else {
                append(text)
            }
            //append("\n")

            //}
            //}
            //withStyle(style = paragraphStyle) {
            //}
        }
    }
}

@Composable
fun SpaceNormal() {
    Spacer(modifier = Modifier.height(24.dp))
}


fun errorMessage(msg: String, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor)) {
            append("¡ERROR! ")
        }
        append(Utils.LS2)
        append(msg)
        append(Utils.toRed(Configuration.MY_GMAIL))
        append(Utils.LS2)
    }
}

fun errorMessageAudio(msg: String): AnnotatedString {
    return buildAnnotatedString {
        append(msg)
        append(Configuration.MY_GMAIL)
        append(".")
    }
}

fun spannableStringToAnnotatedString(text: CharSequence): AnnotatedString {
    return if (text is Spanned) {
        val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
        spanStyles.addAll(text.getSpans(0, text.length, UnderlineSpan::class.java).map {
            AnnotatedString.Range(
                SpanStyle(textDecoration = TextDecoration.Underline),
                text.getSpanStart(it),
                text.getSpanEnd(it)
            )
        })
        spanStyles.addAll(text.getSpans(0, text.length, StyleSpan::class.java).map {
            AnnotatedString.Range(
                SpanStyle(fontWeight = FontWeight.Bold),
                text.getSpanStart(it),
                text.getSpanEnd(it)
            )
        })
        AnnotatedString(text.toString(), spanStyles = spanStyles)
    } else {
        AnnotatedString(text.toString())
    }
}

fun spannableStringToAnnotatedString(
    text: CharSequence,
    density: Density
): AnnotatedString {
    return if (text is Spanned) {
        with(density) {
            buildAnnotatedString {
                append((text.toString()))
                text.getSpans(0, text.length, Any::class.java).forEach {
                    val start = text.getSpanStart(it)
                    val end = text.getSpanEnd(it)
                    when (it) {
                        is StyleSpan -> when (it.style) {
                            Typeface.NORMAL -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal
                                ),
                                start,
                                end
                            )

                            Typeface.BOLD -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                ),
                                start,
                                end
                            )

                            Typeface.ITALIC -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic
                                ),
                                start,
                                end
                            )

                            Typeface.BOLD_ITALIC -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                ),
                                start,
                                end
                            )
                        }

                        is TypefaceSpan -> addStyle(
                            SpanStyle(
                                fontFamily = when (it.family) {
                                    FontFamily.SansSerif.name -> FontFamily.SansSerif
                                    FontFamily.Serif.name -> FontFamily.Serif
                                    FontFamily.Monospace.name -> FontFamily.Monospace
                                    FontFamily.Cursive.name -> FontFamily.Cursive
                                    else -> FontFamily.Default
                                }
                            ),
                            start,
                            end
                        )

                        is BulletSpan -> {
                            //Log.d("StringResources", "BulletSpan not supported yet")
                            addStyle(SpanStyle(), start, end)
                        }

                        is AbsoluteSizeSpan -> addStyle(
                            SpanStyle(fontSize = if (it.dip) it.size.dp.toSp() else it.size.toSp()),
                            start,
                            end
                        )

                        is RelativeSizeSpan -> addStyle(
                            SpanStyle(fontSize = it.sizeChange.em),
                            start,
                            end
                        )

                        is StrikethroughSpan -> addStyle(
                            SpanStyle(textDecoration = TextDecoration.LineThrough),
                            start,
                            end
                        )

                        is UnderlineSpan -> addStyle(
                            SpanStyle(textDecoration = TextDecoration.Underline),
                            start,
                            end
                        )

                        is SuperscriptSpan -> addStyle(
                            SpanStyle(baselineShift = BaselineShift.Superscript),
                            start,
                            end
                        )

                        is SubscriptSpan -> addStyle(
                            SpanStyle(baselineShift = BaselineShift.Subscript),
                            start,
                            end
                        )

                        is ForegroundColorSpan -> addStyle(
                            SpanStyle(color = Color(it.foregroundColor)),
                            start,
                            end
                        )

                        else -> addStyle(SpanStyle(), start, end)
                    }
                }
            }
        }
    } else {
        AnnotatedString(text.toString())
    }
}

fun CharSequence.asAnnotatedString(density: Density): AnnotatedString {
    if (this !is Spanned) return AnnotatedString(this.toString())
    return buildAnnotatedString {
        append(this@asAnnotatedString.toString())
        getSpans(0, length, Any::class.java).forEach {
            val start = getSpanStart(it)
            val end = getSpanEnd(it)

            when (it) {
                is StyleSpan -> when (it.style) {
                    Typeface.NORMAL -> addStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                        start,
                        end
                    )

                    Typeface.BOLD -> addStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                        start,
                        end
                    )

                    Typeface.ITALIC -> addStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic
                        ),
                        start,
                        end
                    )

                    Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        ),
                        start,
                        end
                    )
                }

                is TypefaceSpan -> addStyle(
                    SpanStyle(
                        fontFamily = when (it.family) {
                            FontFamily.SansSerif.name -> FontFamily.SansSerif
                            FontFamily.Serif.name -> FontFamily.Serif
                            FontFamily.Monospace.name -> FontFamily.Monospace
                            FontFamily.Cursive.name -> FontFamily.Cursive
                            else -> FontFamily.Default
                        }
                    ),
                    start,
                    end
                )

                is BulletSpan -> {
                    //Log.d("StringResources", "BulletSpan not supported yet")
                    addStyle(SpanStyle(), start, end)
                }

                is RelativeSizeSpan -> addStyle(
                    SpanStyle(fontSize = it.sizeChange.em),
                    start,
                    end
                )

                is StrikethroughSpan -> addStyle(
                    SpanStyle(textDecoration = TextDecoration.LineThrough),
                    start,
                    end
                )

                is UnderlineSpan -> addStyle(
                    SpanStyle(textDecoration = TextDecoration.Underline),
                    start,
                    end
                )

                is SuperscriptSpan -> addStyle(
                    SpanStyle(baselineShift = BaselineShift.Superscript),
                    start,
                    end
                )

                is SubscriptSpan -> addStyle(
                    SpanStyle(baselineShift = BaselineShift.Subscript),
                    start,
                    end
                )

                is ForegroundColorSpan -> addStyle(
                    SpanStyle(color = Color(it.foregroundColor)),
                    start,
                    end
                )

                else -> addStyle(SpanStyle(), start, end)
            }
            //buildWithSpan(it, start, end)
        }
    }
}

fun contentSpace(lineHeight: Int): AnnotatedString {
    val paragraphStyle = ParagraphStyle(
        lineHeight = lineHeight.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
        }
    }
}


fun contentBody(text: String, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(
        //lineHeight = 25.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )

    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.bodyLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        4 -> NiaTypography.bodyLarge.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
        }
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(text.uppercase())
            }
        }

        withStyle(style = paragraphStyle) {
        }
    }
}


@Composable
fun Int.scaledSp(): TextUnit {
    val value: Int = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}