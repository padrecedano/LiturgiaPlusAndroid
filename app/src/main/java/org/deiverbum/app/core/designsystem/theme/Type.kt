package org.deiverbum.app.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import org.deiverbum.app.core.model.configuration.FontSizeConfig

/**
 * Tipografías personalizadas.
 */
internal fun getPersonalizedTypography(fontSizePrefs: FontSizeConfig): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (57 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((64 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (45 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((52 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (36 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((44 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
        ),
        headlineLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (32 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((40 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (28 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((36 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (24 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((26 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,

            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Bottom,
                trim = LineHeightStyle.Trim.None,
            ),

            ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = (22 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((28 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Bottom,
                trim = LineHeightStyle.Trim.LastLineBottom,
            ),
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = (18 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((24 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.1.sp,
        ),
        titleSmall = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = (14 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((20 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.1.sp,
        ),
        // Default text style
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (16 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((16 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.5.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (14 + fontSizePrefs.fontSizeExtra).sp,
            //lineHeight = ((20 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            lineHeight = TypeScaleTokens.BodyMediumLineHeight,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = (12 + fontSizePrefs.fontSizeExtra).sp,
            //lineHeight = ((12 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            lineHeight = TypeScaleTokens.BodySmallLineHeight,

            letterSpacing = 0.4.sp,
        ),
        // Used for Button
        labelLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = (14 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((20 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.1.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.LastLineBottom,
            ),
        ),
        // Used for Navigation items
        labelMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = (12 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((16 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.5.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.LastLineBottom,
            ),
        ),
        // Used for Tag
        labelSmall = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = (10 + fontSizePrefs.fontSizeExtra).sp,
            lineHeight = ((14 + fontSizePrefs.fontSizeExtra) * lineHeightMultiplier).sp,
            letterSpacing = 0.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.LastLineBottom,
            ),
        ),
    )
}

internal val NiaTypography = Typography(

    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None,
        ),
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    // Default text style
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None,
        ),
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    // Used for Button
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    // Used for Navigation items
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    // Used for Tag
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
)

private const val lineHeightMultiplier = 1.15


internal object TypeScaleTokens {
    val BodyLargeFont = TypefaceTokens.Plain
    val BodyLargeLineHeight = 24.0.sp
    val BodyLargeSize = 16.sp
    val BodyLargeTracking = 0.5.sp
    val BodyLargeWeight = TypefaceTokens.WeightRegular
    val BodyMediumFont = TypefaceTokens.Plain
    val BodyMediumLineHeight = 20.0.sp
    val BodyMediumSize = 14.sp
    val BodyMediumTracking = 0.2.sp
    val BodyMediumWeight = TypefaceTokens.WeightRegular
    val BodySmallFont = TypefaceTokens.Plain
    val BodySmallLineHeight = 16.0.sp
    val BodySmallSize = 12.sp
    val BodySmallTracking = 0.4.sp
    val BodySmallWeight = TypefaceTokens.WeightRegular
    val DisplayLargeFont = TypefaceTokens.Brand
    val DisplayLargeLineHeight = 64.0.sp
    val DisplayLargeSize = 57.sp
    val DisplayLargeTracking = -0.2.sp
    val DisplayLargeWeight = TypefaceTokens.WeightRegular
    val DisplayMediumFont = TypefaceTokens.Brand
    val DisplayMediumLineHeight = 52.0.sp
    val DisplayMediumSize = 45.sp
    val DisplayMediumTracking = 0.0.sp
    val DisplayMediumWeight = TypefaceTokens.WeightRegular
    val DisplaySmallFont = TypefaceTokens.Brand
    val DisplaySmallLineHeight = 44.0.sp
    val DisplaySmallSize = 36.sp
    val DisplaySmallTracking = 0.0.sp
    val DisplaySmallWeight = TypefaceTokens.WeightRegular
    val HeadlineLargeFont = TypefaceTokens.Brand
    val HeadlineLargeLineHeight = 40.0.sp
    val HeadlineLargeSize = 32.sp
    val HeadlineLargeTracking = 0.0.sp
    val HeadlineLargeWeight = TypefaceTokens.WeightRegular
    val HeadlineMediumFont = TypefaceTokens.Brand
    val HeadlineMediumLineHeight = 36.0.sp
    val HeadlineMediumSize = 28.sp
    val HeadlineMediumTracking = 0.0.sp
    val HeadlineMediumWeight = TypefaceTokens.WeightRegular
    val HeadlineSmallFont = TypefaceTokens.Brand
    val HeadlineSmallLineHeight = 32.0.sp
    val HeadlineSmallSize = 24.sp
    val HeadlineSmallTracking = 0.0.sp
    val HeadlineSmallWeight = TypefaceTokens.WeightRegular
    val LabelLargeFont = TypefaceTokens.Plain
    val LabelLargeLineHeight = 20.0.sp
    val LabelLargeSize = 14.sp
    val LabelLargeTracking = 0.1.sp
    val LabelLargeWeight = TypefaceTokens.WeightMedium
    val LabelMediumFont = TypefaceTokens.Plain
    val LabelMediumLineHeight = 16.0.sp
    val LabelMediumSize = 12.sp
    val LabelMediumTracking = 0.5.sp
    val LabelMediumWeight = TypefaceTokens.WeightMedium
    val LabelSmallFont = TypefaceTokens.Plain
    val LabelSmallLineHeight = 16.0.sp
    val LabelSmallSize = 11.sp
    val LabelSmallTracking = 0.5.sp
    val LabelSmallWeight = TypefaceTokens.WeightMedium
    val TitleLargeFont = TypefaceTokens.Brand
    val TitleLargeLineHeight = 28.0.sp
    val TitleLargeSize = 22.sp
    val TitleLargeTracking = 0.0.sp
    val TitleLargeWeight = TypefaceTokens.WeightRegular
    val TitleMediumFont = TypefaceTokens.Plain
    val TitleMediumLineHeight = 24.0.sp
    val TitleMediumSize = 16.sp
    val TitleMediumTracking = 0.2.sp
    val TitleMediumWeight = TypefaceTokens.WeightMedium
    val TitleSmallFont = TypefaceTokens.Plain
    val TitleSmallLineHeight = 20.0.sp
    val TitleSmallSize = 14.sp
    val TitleSmallTracking = 0.1.sp
    val TitleSmallWeight = TypefaceTokens.WeightMedium
}


internal object TypefaceTokens {
    val Brand = FontFamily.SansSerif
    val Plain = FontFamily.SansSerif
    val WeightBold = FontWeight.Bold
    val WeightMedium = FontWeight.Medium
    val WeightRegular = FontWeight.Normal
}
