package org.deiverbum.app.data.source

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.FileRequest

interface FileEntityData {

    suspend fun getFile(fileRequest: FileRequest): String

    suspend fun addFile(homily: String)
}