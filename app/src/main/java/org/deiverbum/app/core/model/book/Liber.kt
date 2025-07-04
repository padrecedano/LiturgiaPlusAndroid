package org.deiverbum.app.core.model.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Liber(
    var title: String = "",
    var bookType: Int,
    var liberTypus: LiberTypus
)


enum class BookType {
    sacramentum,
    cic,
    stream,
    input,
    conversation
}

sealed class NccoAction(
    @Json(name = "typus") val typus: BookType
    //abstract val name: String
)

data class Talk(var numbers: List<SacramentumNumber>) : NccoAction(BookType.sacramentum)
data class Stream(val title: String) : NccoAction(BookType.cic)

@JsonClass(generateAdapter = true)
data class LiberCIC(val title: String, val titles: List<LiberTitle>) : NccoAction(BookType.cic)

sealed class Person {
    @Json(name = "typus")
    abstract val typus: BookType

    abstract val id: Long  // Remove the comma
    abstract val title: String
}

// Now works as expected
data class Developer(
    override val id: Long,
    override val title: String,
    override val typus: BookType
) : Person()


sealed class LiberBasee {
    @JsonClass(generateAdapter = true)
    data class CIC(val typus: String, @Json(name = "contents") val contents: List<LiberContents>) :
        LiberBasee()
}

//@JsonClass(generateAdapter = true)
sealed class LiberContents {
    @JsonClass(generateAdapter = true)
    data class CICTitles(override val type: String) : TextBody

    //data class CICTitleIntro(val intro:List<LiberParagraph>) : CICTitle()
    //@JsonClass(generateAdapter = true)
    //data class UnknownError(val unknownErrorMessage: String) : Error()
}
/*
sealed class CICTitle {
    @JsonClass(generateAdapter = true)
    data class CICTitleIntro(val intro: List<LiberParagraph>) : CICTitle()
    //@JsonClass(generateAdapter = true)
    //data class UnknownError(val unknownErrorMessage: String) : Error()
}
*/
/*sealed class LiberParagraph {
    @JsonClass(generateAdapter = true)
    data class ParagraphWithNumber(val type: String, val n: Int, val txt: List<String>) :
        LiberParagraph()
}*/

abstract class LiberBase(
    open val typus: String,
    open val title: String,
    open val shortTitle: String
)
abstract class LiberTextBase(open val typus: String, open val contents: List<TextBody>)

@JsonClass(generateAdapter = true)
open class ParagraphusBase(
    override val type: String = "base",
    open val txt: String,
    override val isLast: Boolean = false,
    override val isBold: Boolean = false
) : BaseParagraphus, Boldable

@JsonClass(generateAdapter = true)
open class ParagraphusVersiculusResponsum(
    override val type: String = "versiculusResponsum",
    open val items: List<BaseParagraphus>, override val isLast: Boolean = true,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
open class ParagraphusResponsum(
    override val type: String = "responsum",
    open val txt: String, override val isLast: Boolean = false,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
open class ParagraphusVersiculus(
    override val type: String = "versiculus",
    open val txt: String,
    override val isLast: Boolean = false,
    override val isBold: Boolean = true,
) : BaseParagraphus, Boldable

@JsonClass(generateAdapter = true)
open class ParagraphusPriest(
    override val type: String = "priest",
    open val txt: String,
    override val isLast: Boolean = false,
    open val extraSpace: Int = 0,
    open val isBold: Boolean = true
) : BaseParagraphus

@JsonClass(generateAdapter = true)
data class ParagraphusDivider(
    override val type: String = "divider", override val isLast: Boolean = true,
) : BaseParagraphus


@JsonClass(generateAdapter = true)
data class ParagraphusOratio(
    override val type: String,
    override val txt: String,
    override val isLast: Boolean = false,
    override val extraSpace: Int = 0,
    override val isBold: Boolean = true,
    val responsum: String,
) : ParagraphusPriest(type, txt, isLast, extraSpace, isBold)

//: ParagraphusPriest(type, txt, isLast)

@JsonClass(generateAdapter = true)
data class ParagraphusMixtus(
    override val type: String,
    val base: LiberText,
    val replace: List<LiberText>,
    override val isLast: Boolean = false,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
data class ParagraphusPreces(
    override val type: String,
    val txt: List<String>,
    override val isLast: Boolean = false,
    val intro: BaseParagraphus,
    val responsum: String,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
data class ParagraphusDialog(
    override val type: String,
    val txt: List<String>,
    override val isLast: Boolean = false,
    val responsum: String,
    val withDash: Boolean = true,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
data class ParagraphusRationarium(
    override val type: String,
    val txt: List<String>,
    override val isLast: Boolean = false,
    val withDash: Boolean = true,
) : BaseParagraphus

@JsonClass(generateAdapter = true)
open class ParagraphusRubricaNew(
    override val type: String,
    open val txt: String,
    open val extraSpace: Int = 0,
    override val isLast: Boolean = true,
    override val isBold: Boolean = false,

    ) :
    BaseParagraphus, Boldable

@JsonClass(generateAdapter = true)
open class ParagraphusBiblica(
    override val type: String,
    open val txt: String,
    override val isLast: Boolean = false,
) :
    BaseParagraphus

@JsonClass(generateAdapter = true)
data class ParagraphusBiblicaBrevis(
    override val type: String,
    override val txt: String,
    override val isLast: Boolean = false,
    val pericopa: String,
) : ParagraphusBiblica(type, txt, isLast)

@JsonClass(generateAdapter = true)
data class ParagraphusBiblicaLonga(
    override val type: String,
    override val txt: String,
    override val isLast: Boolean = false,
    val book: String,
    val pericopa: String,
) : ParagraphusBiblica(type, txt, isLast)

@JsonClass(generateAdapter = true)
data class ParagraphusRubricaNumerus(
    override val type: String,
    val n: Int,
    override val txt: String,
    override val extraSpace: Int = 0,
    override val isLast: Boolean = false,
    override val isBold: Boolean = false,

    val colorCode: Int = 3
) : ParagraphusRubricaNew(type, txt, extraSpace, isLast)


@JsonClass(generateAdapter = true)
data class LiberTextDefault(
    override val typus: String,
    override val contents: List<TextBody>,
    val isRed: Boolean = false
) : LiberTextBase(typus, contents)

@JsonClass(generateAdapter = true)
data class LiberTextRubric(
    override val typus: String,
    override val contents: List<TextBody>,
    val isRed: Boolean = true
) : LiberTextBase(typus, contents)

@JsonClass(generateAdapter = true)
data class LiberBaseA(
    override val typus: String,
    override val title: String,
    override val shortTitle: String,
    val contents: List<TextBody>
) : LiberBase(typus, title, shortTitle)

@JsonClass(generateAdapter = true)
data class LiberBaseC(
    override val typus: String,
    override val title: String = "",
    val subTitle: String = "",

    override val shortTitle: String = "",
    val sections: List<LiberSectionNew>,
) : LiberBase(typus, title, shortTitle)

@JsonClass(generateAdapter = true)
data class LiberSectionNew(
    val head: BaseHead,
    val paragraphus: List<BaseParagraphus>,
)

@JsonClass(generateAdapter = true)
open class LiberHeadSingle(
    override val type: String,
    open val title: String,
    override val level: Int
) : BaseHead

@JsonClass(generateAdapter = true)
open class LiberHeadComplex(
    override val type: String,
    override val title: String,
    override val level: Int,
    val subTitle: String,
) : LiberHeadSingle(type, title, level)

@JsonClass(generateAdapter = true)
data class LiberHeadBlank(
    override val type: String = "blank",
    override val level: Int = 0,
) : BaseHead


@JsonClass(generateAdapter = true)
data class LiberError(
    override val typus: String = "error",
    override val title: String,
    override val shortTitle: String,
    val msg: String
) : LiberBase(typus, title, shortTitle)

abstract class ContentNew(open val type: String)

@JsonClass(generateAdapter = true)
data class CICTitlesNew(override val type: String, val contents: List<CICData>) : ContentNew(type)

interface TextBody {
    val type: String
}

interface BaseParagraphus {
    val type: String
    val isLast: Boolean
}

interface Boldable {
    val isBold: Boolean
}

interface BaseHead {
    val type: String
    val level: Int
}

@JsonClass(generateAdapter = true)
open class Canon(
    @Json(name = "type") override val type: String,
    open val n: Int,
    open val txt: List<String>
) : TextBody

@JsonClass(generateAdapter = true)
data class CanonWithList(
    @Json(name = "type") override val type: String,
    override val n: Int,
    override val txt: List<String>,
    val list: List<String>
) : Canon(type, n, txt)

@JsonClass(generateAdapter = true)
class Title(
    @Json(name = "type") override val type: String,
    val n: Int,
    val title: String,
    val contents: List<TextBody>
) : TextBody

@JsonClass(generateAdapter = true)
class IurisChapter(
    @Json(name = "type") override val type: String,
    val n: Int,
    val title: String,
    val contents: List<TextBody>
) : TextBody

abstract class BookBody(open val type: String)

@JsonClass(generateAdapter = true)
data class CICData(override val type: String, val txt: List<String>, val n: Int) : BookBody(type)

@JsonClass(generateAdapter = true)
data class LiberSacramentumNew(
    override val typus: String, override val title: String,
    override val shortTitle: String, val contents: List<TextBody>
) : LiberBase(typus, title, shortTitle)

@JsonClass(generateAdapter = true)
class LiberSection(
    @Json(name = "type") override val type: String,
    val title: String = "",
    val subTitle: String = "",
    val contents: List<TextBody>
) : TextBody

@JsonClass(generateAdapter = true)
open class Rubrica(@Json(name = "type") override val type: String, open val txt: List<String>) :
    TextBody

@JsonClass(generateAdapter = true)
open class RubricaNumerus(
    @Json(name = "type") override val type: String, val n: Int,
    override val txt: List<String>, val extraSpace: Boolean = true
) : Rubrica(type, txt)

@JsonClass(generateAdapter = true)
data class Priest(
    @Json(name = "type") override val type: String,
    val txt: List<String>,
    val isBold: Boolean = false,
    val lineBreak: Int = 1
) : TextBody

@JsonClass(generateAdapter = true)
class LiberOratio(
    @Json(name = "type") override val type: String,
    val txt: List<String>,
    val responsum: String,
    val isBold: Boolean = false,
    val lineBreak: Int = 0
) : TextBody

@JsonClass(generateAdapter = true)
class LiberOratioo(
    override val type: String,
    val paragraphus: List<LiberParagraphus>,
    val paragraphuss: List<ParagraphusBase>,

    val responsum: String,
    val isBold: Boolean = false,
    val lineBreak: Int = 0
) : TextBody

@JsonClass(generateAdapter = true)
class LiberParagraphus(
    override val type: String,
    val txt: List<String>
) : TextBody

@JsonClass(generateAdapter = true)
class LiberBiblical(
    @Json(name = "type") override val type: String,
    val book: String,
    val pericopa: String,
    val txt: List<String>
) : TextBody

@JsonClass(generateAdapter = true)
data class LiberPreces(
    override val type: String,
    val intro: String,
    override var responsum: String,
    override var txt: List<String>,
    override var isBold: Boolean = false
) : LiberDialog(type = type, responsum = responsum, txt = txt, isBold = isBold)

@JsonClass(generateAdapter = true)
open class LiberDialog(
    override val type: String,
    open var responsum: String,
    open var txt: List<String>,
    open var isBold: Boolean = false
) : TextBody

@JsonClass(generateAdapter = true)
data class LiberDupla(
    val txt: String,
    val responsum: String,
)

@JsonClass(generateAdapter = true)
data class LiberBodyWithRubric(
    override val type: String,
    val dupla: List<Dupla>,
) : TextBody

@JsonClass(generateAdapter = true)
data class LiberMixtus(
    override val type: String,
    val data: List<LiberText>,
) : TextBody

@JsonClass(generateAdapter = true)
data class LiberMixtusB(
    override val type: String,
    val base: LiberText,
    val replace: List<LiberText>,
) : TextBody


@JsonClass(generateAdapter = true)
data class LiberText(
    override val type: String = "base",
    val txt: String,
    val isRed: Boolean = false,
    val isBold: Boolean = false,
    val lineBreak: Int = 0,
) : TextBody