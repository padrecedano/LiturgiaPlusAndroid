package org.deiverbum.app.core.data.source.local

import android.text.SpannableStringBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.deiverbum.app.core.data.source.FileEntityData
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse
import org.deiverbum.app.core.model.data.OracionSimple
import org.deiverbum.app.core.model.data.Rosario
import org.deiverbum.app.core.model.data.ViaCrucis
import org.deiverbum.app.core.model.data.book.Book
import org.deiverbum.app.util.AssetProvider
import org.deiverbum.app.util.Constants.DATA_NOTFOUND
import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_ANGELUS
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_LITANIES
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_REGINA
import org.deiverbum.app.util.Constants.FILE_ROSARY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2003
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2005
import javax.inject.Inject

/**
 * Fuente de datos local para los archivos.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class LocalFileEntityData @Inject constructor(
    private val assetProvider: AssetProvider
) : FileEntityData {
    private val books = listOf(
        FILE_ABOUT, FILE_AUTHOR, FILE_HELP,
        FILE_NEW, FILE_PRIVACY, FILE_TERMS, FILE_THANKS
    )
    private val viacrucis = listOf(FILE_VIA_CRUCIS_2003, FILE_VIA_CRUCIS_2005)
    private val pray = listOf(FILE_ANGELUS, FILE_REGINA, FILE_LITANIES)

    override suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse> {
        val fileResponse = assetProvider.getFiles(fileRequest.fileName)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        fileResponse.forEach {
            if (books.contains(it.fileName)) {
                it.text = moshi.adapter(Book::class.java).fromJson(it.text.toString())!!
                    .getForView(fileRequest.isNightMode)
            } else if (viacrucis.contains(it.fileName)) {
                it.text = moshi.adapter(ViaCrucis::class.java).fromJson(it.text.toString())!!
                    .getForView(fileRequest.isNightMode)
            } else if (it.fileName == FILE_ROSARY) {
                val data: Rosario =
                    moshi.adapter(Rosario::class.java).fromJson(it.text.toString())!!
                data.day = fileRequest.dayOfWeek
                it.text = data.getForView(fileRequest.isBrevis, fileRequest.isNightMode)
            } else if (pray.contains(it.fileName)) {
                it.text = moshi.adapter(OracionSimple::class.java).fromJson(it.text.toString())!!
                    .getForView(fileRequest.isNightMode)
            } else {
                it.text = SpannableStringBuilder(DATA_NOTFOUND)
            }
        }
        return fileResponse
    }

    override suspend fun addFile(fileName: String) {
    }
}