package org.deiverbum.app.util

import android.content.Context
import android.text.SpannableStringBuilder
import org.deiverbum.app.data.model.FileResponse
import javax.inject.Inject

class AssetProvider @Inject constructor(
    private val context: Context
) {

    fun getFiles(filesPath: List<String>): MutableList<FileResponse> {
        val fileResponses = mutableListOf<FileResponse>()
        return try {
            filesPath.forEach {
                context.assets.open(it.toString()).use { inputStream ->
                    inputStream.reader().use { reader ->
                        fileResponses.add(
                            FileResponse(
                                SpannableStringBuilder(reader.readText()),
                                it.toString()
                            )
                        )
                    }

                }
            }
            fileResponses
        } catch (ex: Exception) {
            fileResponses.add(FileResponse(SpannableStringBuilder(),""))
            fileResponses
        }
    }

}
