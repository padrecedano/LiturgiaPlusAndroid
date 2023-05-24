package org.deiverbum.app.data.source

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest

interface FileEntityData {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>

    suspend fun addFile(file: String)
}