package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import com.google.gson.Gson
import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.data.source.FileEntityData
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.model.Book
import org.deiverbum.app.model.OracionSimple
import org.deiverbum.app.model.Rosario
import org.deiverbum.app.model.ViaCrucis
import org.deiverbum.app.util.AssetProvider
import org.deiverbum.app.utils.Constants.DATA_NOTFOUND
import javax.inject.Inject

/**
 * <p>Fuente de datos local para los archivos.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class LocalFileEntityData @Inject constructor(
    private val assetProvider: AssetProvider
) : FileEntityData {

    override suspend fun getFile(fileRequest: FileRequest): FileResponse {
        val fileResponse = assetProvider.getFile(fileRequest.fileName)
        when (fileRequest.fileName) {
            "raw/about_202201.json","raw/author_202201.json","raw/help_202201.json","raw/new_202301.json",
            "raw/privacy_202301.json","raw/terms_202301.json","raw/thanks_202201.json"-> {
                val data: Book = Gson().fromJson(fileResponse.text.toString(), Book::class.java)
                fileResponse.text=data.getForView(false)
                return fileResponse
            }
            "raw/viacrucis2003.json","raw/viacrucis2005.json" -> {
                val data: ViaCrucis = Gson().fromJson(fileResponse.text.toString(), ViaCrucis::class.java)
                fileResponse.text= data.getForView(fileRequest.isNightMode)
                return fileResponse
            }
            "raw/rosario.json" -> {
                val data: Rosario = Gson().fromJson(fileResponse.text.toString(), Rosario::class.java)
                data.day=fileRequest.dayOfWeek
                fileResponse.text= data.getForView(fileRequest.isBrevis,fileRequest.isNightMode)
                return fileResponse
            }
            "raw/letanias.json","raw/angelus.json","raw/regina.json" -> {
                val data: OracionSimple = Gson().fromJson(fileResponse.text.toString(), OracionSimple::class.java)
                fileResponse.text= data.getForView(fileRequest.isNightMode)
                return fileResponse
            }
            else -> {
                fileResponse.text=SpannableStringBuilder(DATA_NOTFOUND)
            }
        }
        return fileResponse
    }

    override suspend fun addFile(homily: String) {
    }
}