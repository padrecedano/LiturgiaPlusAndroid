package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import org.deiverbum.app.core.designsystem.component.contentTitle
import org.deiverbum.app.core.designsystem.component.h
import org.deiverbum.app.core.designsystem.component.sectionTitle
import org.deiverbum.app.core.designsystem.theme.NiaTypography

data class IntroitusNew(val content: String, var placeHolder: String) {
    companion object {
        fun createIntroitus() =
            IntroitusNew(content = "V./ Dios mío, ven en mi auxilio*.", placeHolder = "V./")
    }
}


abstract class LiturgiaContent
    (open val text: String, open val userData: UserDataDynamic?) {
    open fun getHeaders(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun forView(calendarTime: Int): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}


abstract class LiturgyContent(open val text: String /*open val style:TextStyle*/)
data class ContentHead(
    override val text: String, override val userData: UserDataDynamic? = null,
    val level: Int
) :
    LiturgiaContent(text, userData) {
    val textStyle: TextStyle
        get() = when (level) {
            1 -> NiaTypography.headlineLarge
            2 -> NiaTypography.headlineMedium
            3 -> NiaTypography.headlineSmall
            4 -> NiaTypography.displayLarge
            5 -> NiaTypography.displayMedium
            6 -> NiaTypography.displaySmall
            else -> NiaTypography.bodyLarge
        }

    @Composable
    fun getComposable() {
        return h(this)
    }

    fun forView() {
        //return super.getHeaders()
        val result = when (level) {
            1 -> NiaTypography.headlineLarge
            2 -> NiaTypography.headlineMedium
            3 -> NiaTypography.headlineSmall
            4 -> NiaTypography.displayLarge
            5 -> NiaTypography.displayMedium
            6 -> NiaTypography.displaySmall
            else -> NiaTypography.bodyLarge
        }

    }

}

data class ContentTitle(
    override val text: String, override val userData: UserDataDynamic? = null,
    val level: Int
) :
    LiturgiaContent(text, userData) {
    val textStyle: TextStyle
        get() = when (level) {
            1 -> NiaTypography.titleLarge
            2 -> NiaTypography.titleMedium
            3 -> NiaTypography.titleSmall
            else -> NiaTypography.bodyLarge
        }

    @Composable
    fun getComposable() {
        return contentTitle(this)
    }

    fun forView() {
        //return super.getHeaders()
        val result = when (level) {
            1 -> NiaTypography.titleLarge
            2 -> NiaTypography.titleMedium
            3 -> NiaTypography.titleSmall
            else -> NiaTypography.bodyLarge
        }

    }

}

data class SectionTitle(
    override val text: String,
    val level: Int
) :
    LiturgiaContent(text, null) {
    val textStyle: TextStyle
        get() = when (level) {
            1 -> NiaTypography.titleLarge
            2 -> NiaTypography.titleMedium
            3 -> NiaTypography.titleSmall
            else -> NiaTypography.bodyLarge
        }

    @Composable
    fun getComposable() {
        return sectionTitle(this)
    }

    fun forView() {
        //return super.getHeaders()
        val result = when (level) {
            1 -> NiaTypography.titleLarge
            2 -> NiaTypography.titleMedium
            3 -> NiaTypography.titleSmall
            else -> NiaTypography.bodyLarge
        }

    }

}


//data class Intro(override val text: String): LiturgyContent(text)

//data class BodyContent(override val text: String,  val data2: String): LiturgyContent(text)
data class HeadContent(
    override val text: String,
    val level: Int /*override val style:TextStyle,*/
) : LiturgyContent(text) {
    val result = when (level) {
        1 -> NiaTypography.headlineLarge
        2 -> NiaTypography.headlineMedium
        3 -> NiaTypography.headlineSmall
        4 -> NiaTypography.displayLarge
        5 -> NiaTypography.displayMedium
        6 -> NiaTypography.displaySmall
        else -> NiaTypography.bodyLarge

    }
    //override val style= result
}