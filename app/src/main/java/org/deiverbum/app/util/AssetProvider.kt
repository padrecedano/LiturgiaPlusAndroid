package org.deiverbum.app.util

import android.content.Context
import android.text.SpannableStringBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.core.model.book.BaseHead
import org.deiverbum.app.core.model.book.BaseParagraphus
import org.deiverbum.app.core.model.book.Canon
import org.deiverbum.app.core.model.book.CanonWithList
import org.deiverbum.app.core.model.book.IurisChapter
import org.deiverbum.app.core.model.book.LiberBase
import org.deiverbum.app.core.model.book.LiberBaseA
import org.deiverbum.app.core.model.book.LiberBaseC
import org.deiverbum.app.core.model.book.LiberBiblical
import org.deiverbum.app.core.model.book.LiberBodyWithRubric
import org.deiverbum.app.core.model.book.LiberDialog
import org.deiverbum.app.core.model.book.LiberError
import org.deiverbum.app.core.model.book.LiberHeadBlank
import org.deiverbum.app.core.model.book.LiberHeadComplex
import org.deiverbum.app.core.model.book.LiberHeadSingle
import org.deiverbum.app.core.model.book.LiberMixtus
import org.deiverbum.app.core.model.book.LiberMixtusB
import org.deiverbum.app.core.model.book.LiberOratio
import org.deiverbum.app.core.model.book.LiberOratioo
import org.deiverbum.app.core.model.book.LiberPreces
import org.deiverbum.app.core.model.book.LiberSection
import org.deiverbum.app.core.model.book.LiberText
import org.deiverbum.app.core.model.book.LiberTextBase
import org.deiverbum.app.core.model.book.LiberTextDefault
import org.deiverbum.app.core.model.book.ParagraphusBase
import org.deiverbum.app.core.model.book.ParagraphusBiblicaBrevis
import org.deiverbum.app.core.model.book.ParagraphusBiblicaLonga
import org.deiverbum.app.core.model.book.ParagraphusDialog
import org.deiverbum.app.core.model.book.ParagraphusDivider
import org.deiverbum.app.core.model.book.ParagraphusMixtus
import org.deiverbum.app.core.model.book.ParagraphusOratio
import org.deiverbum.app.core.model.book.ParagraphusPreces
import org.deiverbum.app.core.model.book.ParagraphusPriest
import org.deiverbum.app.core.model.book.ParagraphusRationarium
import org.deiverbum.app.core.model.book.ParagraphusResponsum
import org.deiverbum.app.core.model.book.ParagraphusRubricaNew
import org.deiverbum.app.core.model.book.ParagraphusRubricaNumerus
import org.deiverbum.app.core.model.book.ParagraphusVersiculus
import org.deiverbum.app.core.model.book.ParagraphusVersiculusResponsum
import org.deiverbum.app.core.model.book.Priest
import org.deiverbum.app.core.model.book.RubricaNumerus
import org.deiverbum.app.core.model.book.TextBody
import org.deiverbum.app.core.model.book.Title
import org.deiverbum.app.core.model.data.FileItem
import org.deiverbum.app.core.model.data.FileResourceNew
import org.deiverbum.app.core.model.data.FileResponse
import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_SACERDOS
import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_COMMENDATIONE_MORIENTIUM
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_ARTICULO_MORTIS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_IN_DUBIO
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_SINE_VIATICUM
import org.deiverbum.app.util.Constants.UNCTIONIS_ORDINARIUM
import javax.inject.Inject

class AssetProvider @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val books = listOf(
        FILE_ABOUT, FILE_AUTHOR, FILE_HELP,
        FILE_NEW, FILE_PRIVACY, FILE_TERMS, FILE_THANKS
    )

    private val cic = listOf(
        CIC_BAPTISMUS, CIC_UNCTIONIS
    )

    private val sacramentum = listOf(
        FILE_BAPTISMUS, UNCTIONIS_ORDINARIUM,
        FILE_UNCTIONIS_ARTICULO_MORTIS, FILE_UNCTIONIS_SINE_VIATICUM,
        FILE_UNCTIONIS_IN_DUBIO,
        FILE_COMMENDATIONE_MORIENTIUM, EUCHARISTIA_VIATICUM_SACERDOS
    )

    fun getFiles(filesPath: List<FileItem>): List<FileResponse> {
        val fileResponses = mutableListOf<FileResponse>()
        return try {
            filesPath.forEach {
                context.assets.open(it.fileName).use { inputStream ->
                    inputStream.reader().use { reader ->
                        fileResponses.add(
                            FileResponse(
                                SpannableStringBuilder(reader.readText()),
                                it.fileTitle,
                                it.fileName
                            )
                        )
                    }

                }
            }
            fileResponses
        } catch (ex: Exception) {
            fileResponses.add(FileResponse(SpannableStringBuilder(ex.message), "", ""))
            fileResponses
        }
    }

    fun getFilesNew(fileItem: FileItem): FileResourceNew {
        return try {
            var moshii = Moshi.Builder()

                .add(
                    PolymorphicJsonAdapterFactory.of(LiberBase::class.java, "typus")
                        .withSubtype(LiberBaseA::class.java, "cic")
                        //.withSubtype(LiberSacramentumNew::class.java, "sacramentum")
                        .withSubtype(LiberBaseC::class.java, "sacramentum")
                        .withSubtype(LiberBaseC::class.java, "oratio")

                )
                .add(
                    PolymorphicJsonAdapterFactory.of(BaseParagraphus::class.java, "type")
                        .withSubtype(ParagraphusBase::class.java, "base")
                        .withSubtype(ParagraphusRubricaNew::class.java, "rubrica")
                        .withSubtype(ParagraphusRubricaNumerus::class.java, "rubricaNumerus")
                        .withSubtype(ParagraphusPriest::class.java, "priest")
                        .withSubtype(ParagraphusOratio::class.java, "oratio")
                        .withSubtype(ParagraphusDialog::class.java, "dialog")
                        .withSubtype(ParagraphusRationarium::class.java, "rationarium")

                        .withSubtype(ParagraphusPreces::class.java, "preces")
                        .withSubtype(ParagraphusMixtus::class.java, "mixtus")
                        .withSubtype(ParagraphusBiblicaBrevis::class.java, "biblicalShort")
                        .withSubtype(ParagraphusBiblicaLonga::class.java, "biblicaLonga")

                        .withSubtype(
                            ParagraphusVersiculusResponsum::class.java,
                            "versiculusResponsum"
                        )
                        .withSubtype(ParagraphusVersiculus::class.java, "versiculus")
                        .withSubtype(ParagraphusResponsum::class.java, "responsum")
                        .withSubtype(ParagraphusDivider::class.java, "divider")

                )
                .add(
                    PolymorphicJsonAdapterFactory.of(BaseHead::class.java, "type")
                        .withSubtype(LiberHeadSingle::class.java, "single")
                        .withSubtype(LiberHeadComplex::class.java, "complex")
                        .withSubtype(LiberHeadBlank::class.java, "blank")

                )
                .add(
                    PolymorphicJsonAdapterFactory.of(LiberTextBase::class.java, "typus")
                        .withSubtype(LiberTextDefault::class.java, "base")
                    //.withSubtype(LiberTextRubric::class.java, "rubric")
                )
                .add(
                    PolymorphicJsonAdapterFactory.of(TextBody::class.java, "type")
                        .withSubtype(Canon::class.java, "canon")
                        .withSubtype(CanonWithList::class.java, "canonWithList")
                        .withSubtype(Canon::class.java, "paragraph")
                        .withSubtype(Canon::class.java, "body")
                        .withSubtype(Title::class.java, "title")
                        .withSubtype(IurisChapter::class.java, "chapter")
                        .withSubtype(LiberSection::class.java, "section")
                        .withSubtype(
                            org.deiverbum.app.core.model.book.Rubrica::class.java,
                            "rubrica"
                        )
                        .withSubtype(RubricaNumerus::class.java, "rubricWithNumber")
                        .withSubtype(Priest::class.java, "priest")
                        .withSubtype(LiberOratio::class.java, "oratio")
                        .withSubtype(LiberOratioo::class.java, "oratioo")

                        .withSubtype(LiberPreces::class.java, "preces")
                        .withSubtype(Priest::class.java, "responsum")
                        .withSubtype(LiberDialog::class.java, "dialog")
                        .withSubtype(LiberBodyWithRubric::class.java, "bodyWithRubric")
                        .withSubtype(LiberMixtus::class.java, "mixtus")
                        .withSubtype(LiberMixtusB::class.java, "mixtusB")
                        .withSubtype(LiberText::class.java, "base")

                        //.withSubtype(LiberText::class.java, "text")


                        .withSubtype(LiberBiblical::class.java, "biblical")
                )
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonString = context.assets.open(fileItem.fileName)
                .bufferedReader()
                .use { it.readText() }
            FileResourceNew(moshii.adapter(LiberBase::class.java).fromJson(jsonString)!!)
        } catch (ex: Exception) {
            FileResourceNew(LiberError("error", "Error", "", ex.message.toString()))
        }
    }
}
