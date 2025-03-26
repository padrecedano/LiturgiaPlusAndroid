package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.book.Book
import org.deiverbum.app.core.model.book.LiberBase
import org.deiverbum.app.core.model.configuration.UserData

class FileResponse(
    var text: SpannableStringBuilder = SpannableStringBuilder(),
    var title: String,
    var fileName: String

)


data class NewFileResponse(
    val fileResponse: List<FileResponse>,
    val userData: UserData,
    val new: FileResourceNew
)


/**
 * External data layer representation of a fully populated NiA news resource
 */
data class FileResource(
    //val todayDate: Int,
    val data: List<Book>,
)


/**
 * External data layer representation of a fully populated NiA news resource
 */
data class FileResourceNew(
    //val todayDate: Int,
    val data: LiberBase,
)
