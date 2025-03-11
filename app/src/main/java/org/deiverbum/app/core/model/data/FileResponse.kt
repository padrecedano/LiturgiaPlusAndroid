package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.book.Book

class FileResponse(
    var text: SpannableStringBuilder = SpannableStringBuilder(),
    var title: String,
    var fileName: String

)


data class NewFileResponse(
    val fileResponse: List<FileResponse>,
    val userData: UserData,
    val new: FileResource
)


/**
 * External data layer representation of a fully populated NiA news resource
 */
data class FileResource(
    //val todayDate: Int,
    val data: List<Book>,
)


