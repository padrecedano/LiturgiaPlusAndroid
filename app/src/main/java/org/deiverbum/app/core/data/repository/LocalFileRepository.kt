package org.deiverbum.app.core.data.repository

import android.text.SpannableStringBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.deiverbum.app.core.model.alteri.Rosario
import org.deiverbum.app.core.model.alteri.ViaCrucis
import org.deiverbum.app.core.model.book.Book
import org.deiverbum.app.core.model.book.BookSacramentum
import org.deiverbum.app.core.model.book.LiberFileNormalis
import org.deiverbum.app.core.model.book.LiberTypus
import org.deiverbum.app.core.model.cic.IurisCanonici
import org.deiverbum.app.core.model.data.FileRequestt
import org.deiverbum.app.core.model.data.FileResourceNew
import org.deiverbum.app.core.model.data.FileResponse
import org.deiverbum.app.core.model.liturgia.OracionSimple
import org.deiverbum.app.core.model.ritualis.Biblical
import org.deiverbum.app.core.model.ritualis.BiblicalShort
import org.deiverbum.app.core.model.ritualis.Content
import org.deiverbum.app.core.model.ritualis.Oratio
import org.deiverbum.app.core.model.ritualis.Paragraphus
import org.deiverbum.app.core.model.ritualis.ParagraphusList
import org.deiverbum.app.core.model.ritualis.ParagraphusRubrica
import org.deiverbum.app.core.model.ritualis.Preces
import org.deiverbum.app.core.model.ritualis.Ritualis
import org.deiverbum.app.core.model.ritualis.Rubrica
import org.deiverbum.app.core.model.ritualis.Titulus
import org.deiverbum.app.core.model.ritualis.VersiculusEtResponsum
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import org.deiverbum.app.util.AssetProvider
import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.DATA_NOTFOUND
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_SACERDOS
import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_ANGELUS
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_COMMENDATIONE_MORIENTIUM
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_LITANIES
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_REGINA
import org.deiverbum.app.util.Constants.FILE_ROSARY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_ARTICULO_MORTIS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_IN_DUBIO
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_SINE_VIATICUM
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2003
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2005
import org.deiverbum.app.util.Constants.UNCTIONIS_ORDINARIUM
import javax.inject.Inject

/**
 * Repositorio de archivos locales.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class LocalFileRepository @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,

    private val assetProvider: AssetProvider
) : FileRepository {
    private val books = listOf(
        FILE_ABOUT, FILE_AUTHOR, FILE_HELP,
        FILE_NEW, FILE_PRIVACY, FILE_TERMS, FILE_THANKS
    )
    private val sacramentum = listOf(
        FILE_BAPTISMUS
    )
    private val cic = listOf(
        CIC_BAPTISMUS,
        CIC_UNCTIONIS
    )
    private val ritualis = listOf(
        UNCTIONIS_ORDINARIUM,
        EUCHARISTIA_VIATICUM_SACERDOS,
        FILE_UNCTIONIS_ARTICULO_MORTIS,
        FILE_UNCTIONIS_SINE_VIATICUM,
        FILE_UNCTIONIS_IN_DUBIO,
        FILE_COMMENDATIONE_MORIENTIUM

    )
    private val viacrucis = listOf(FILE_VIA_CRUCIS_2003, FILE_VIA_CRUCIS_2005)
    private val pray = listOf(FILE_ANGELUS, FILE_REGINA, FILE_LITANIES)

    private val moshi = Moshi.Builder()
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
        .add(
            PolymorphicJsonAdapterFactory.of(LiberTypus::class.java, "typus")
                .withSubtype(LiberFileNormalis::class.java, "normalis")
        )
        .add(KotlinJsonAdapterFactory())
        .build()

    override suspend fun getFile(fileRequest: FileRequestt): List<FileResponse> =
        withContext(ioDispatcher) {
            val fileResponse = assetProvider.getFiles(listOf(fileRequest.fileName))

            fileResponse.forEach {
                if (books.contains(it.fileName)) {
                    it.text = moshi.adapter(Book::class.java).fromJson(it.text.toString())!!
                        .getForView(fileRequest.isNightMode)
                } else if (viacrucis.contains(it.title)) {
                    it.text = moshi.adapter(ViaCrucis::class.java).fromJson(it.text.toString())!!
                        .getForView(fileRequest.isNightMode)
                } else if (it.title == FILE_ROSARY) {
                    val data: Rosario =
                        moshi.adapter(Rosario::class.java).fromJson(it.text.toString())!!
                    data.day = fileRequest.dayOfWeek
                    it.text = data.getForView(fileRequest.isBrevis, fileRequest.isNightMode)
                } else if (pray.contains(it.title)) {
                    it.text =
                        moshi.adapter(OracionSimple::class.java).fromJson(it.text.toString())!!
                            .getForView(fileRequest.isNightMode)
                } else if (sacramentum.contains(it.title)) {
                    it.text =
                        moshi.adapter(BookSacramentum::class.java).fromJson(it.text.toString())!!
                            .getForView(fileRequest.isNightMode)
                } else if (ritualis.contains(it.title)) {
                    it.text = moshi.adapter(Ritualis::class.java).fromJson(it.text.toString())!!
                        .getForView(fileRequest.isNightMode)
                } else if (cic.contains(it.title)) {
                    it.text =
                        moshi.adapter(IurisCanonici::class.java).fromJson(it.text.toString())!!
                            .getForView(fileRequest.isNightMode)
                } else {
                    it.text = SpannableStringBuilder(DATA_NOTFOUND)
                }
            }
            fileResponse

        }

    override suspend fun getFileModel(fileRequest: FileRequestt): FileResourceNew =

        //val fileResource=FileResource()
        withContext(ioDispatcher) {
            assetProvider.getFilesNew(fileRequest.fileName)

        }

}
//return TODO("Provide the return value")


