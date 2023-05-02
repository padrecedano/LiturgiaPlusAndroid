package org.deiverbum.app.domain.repository

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest

interface FileRepository {

    suspend fun getFile(fileRequest: FileRequest): FileResponse
}