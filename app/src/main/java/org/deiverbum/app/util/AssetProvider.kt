package org.deiverbum.app.util

import android.content.Context
import android.text.SpannableStringBuilder
import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.utils.Constants
import javax.inject.Inject

class AssetProvider @Inject constructor(
    private val context: Context
) {

    fun getFile(filePath:String): FileResponse {
        val fileResponse=FileResponse()
        try {

            context.assets.open(filePath).use { inputStream ->
                fileResponse.text = inputStream.reader().use { reader ->
                    SpannableStringBuilder(reader.readText())
                }

                }
            return fileResponse


        } catch (ex: Exception) {
            //Log.e("TAG", "Error seeding database", ex)
            return FileResponse(SpannableStringBuilder(Constants.ERR_FILE_NOT_FOUND),false)

        }
    }

}
