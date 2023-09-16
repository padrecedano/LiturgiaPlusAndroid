package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import com.google.gson.Gson
import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.data.source.FileEntityData
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.model.*
import org.deiverbum.app.util.AssetProvider
import org.deiverbum.app.util.Constants.*
import javax.inject.Inject

/**
 * <p>Fuente de datos local para los archivos.</p>
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
        fileResponse.forEach {
            if (books.contains(it.fileName)) {
                val data: Book = Gson().fromJson(it.text.toString(), Book::class.java)
                it.text = data.getForView(fileRequest.isNightMode)
            } else if (viacrucis.contains(it.fileName)) {
                val data: ViaCrucis = Gson().fromJson(it.text.toString(), ViaCrucis::class.java)
                it.text = data.getForView(fileRequest.isNightMode)
            } else if (it.fileName == "raw/completas.json") {
                val hora: Completas = Gson().fromJson(it.text.toString(), Completas::class.java)
                hora.setTypeId(7)
                //hora.setToday(fileRequest.);

                it.text = hora.getAllForView()
            } else if (it.fileName == FILE_ROSARY) {
                val data: Rosario = Gson().fromJson(it.text.toString(), Rosario::class.java)
                data.day = fileRequest.dayOfWeek
                it.text = data.getForView(fileRequest.isBrevis, fileRequest.isNightMode)
            } else if (pray.contains(it.fileName)) {
                val data: OracionSimple =
                    Gson().fromJson(it.text.toString(), OracionSimple::class.java)
                it.text = data.getForView(fileRequest.isNightMode)
            } else {
                it.text = SpannableStringBuilder(DATA_NOTFOUND)
            }
        }
        return fileResponse
    }

    override suspend fun addFile(fileName: String) {
    }
}