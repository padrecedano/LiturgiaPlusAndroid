package org.deiverbum.app.util

import android.content.Context
import android.text.SpannableStringBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.core.model.data.FileItem
import org.deiverbum.app.core.model.data.FileResource
import org.deiverbum.app.core.model.data.FileResponse
import org.deiverbum.app.core.model.data.book.Book
import org.deiverbum.app.core.model.data.ritualis.Biblical
import org.deiverbum.app.core.model.data.ritualis.BiblicalShort
import org.deiverbum.app.core.model.data.ritualis.Content
import org.deiverbum.app.core.model.data.ritualis.Oratio
import org.deiverbum.app.core.model.data.ritualis.Paragraphus
import org.deiverbum.app.core.model.data.ritualis.ParagraphusList
import org.deiverbum.app.core.model.data.ritualis.ParagraphusRubrica
import org.deiverbum.app.core.model.data.ritualis.Preces
import org.deiverbum.app.core.model.data.ritualis.Rubrica
import org.deiverbum.app.core.model.data.ritualis.Titulus
import org.deiverbum.app.core.model.data.ritualis.VersiculusEtResponsum
import javax.inject.Inject

class AssetProvider @Inject constructor(
    @ApplicationContext val context: Context
) {

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

    fun getFilesNew(filesPath: List<FileItem>): FileResource {
        val fileResponses = mutableListOf<Book>()
        val moshi = Moshi.Builder()
            .add(

                PolymorphicJsonAdapterFactory.of(Content::class.java, "type")
                    .withSubtype(Paragraphus::class.java, "p")
                    .withSubtype(Rubrica::class.java, "r")
                    .withSubtype(Titulus::class.java, "t")
                    .withSubtype(Biblical::class.java, "biblical")
                    .withSubtype(ParagraphusList::class.java, "pl")
                    .withSubtype(VersiculusEtResponsum::class.java, "vr")
                    .withSubtype(ParagraphusRubrica::class.java, "pr")
                    .withSubtype(Preces::class.java, "preces")
                    .withSubtype(Oratio::class.java, "oratio")
                    .withSubtype(BiblicalShort::class.java, "biblicalShort")
            )
            //.add(
            ///   PolymorphicJsonAdapterFactory.of(LiberTypus::class.java, "typus")
            //       .withSubtype(LiberFileNormalis::class.java, "normalis"))
            .add(KotlinJsonAdapterFactory())
            .build()

        return try {
            filesPath.forEach {
                context.assets.open(it.fileName).use { inputStream ->
                    inputStream.reader().use { reader ->
                        var t = moshi.adapter(Book::class.java).fromJson(reader.readText())
                        fileResponses.add(
                            t!!

                        )

                    }

                }
            }
            FileResource(fileResponses)
        } catch (ex: Exception) {
            //fileResponses.add(Book())
            FileResource(fileResponses)
        }
    }

}
