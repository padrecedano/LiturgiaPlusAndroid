package org.deiverbum.app.util

import android.content.Context
import android.text.SpannableStringBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.core.model.FileResponse
import javax.inject.Inject

class AssetProvider @Inject constructor(
    @ApplicationContext val context: Context

    //private val context: Context
) {

    fun getFiles(filesPath: List<String>): MutableList<FileResponse> {
        val fileResponses = mutableListOf<FileResponse>()
        return try {
            filesPath.forEach {
                context.assets.open(it).use { inputStream ->
                    inputStream.reader().use { reader ->
                        fileResponses.add(
                            FileResponse(
                                SpannableStringBuilder(reader.readText()),
                                it
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
