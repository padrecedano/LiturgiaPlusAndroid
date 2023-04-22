package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import org.deiverbum.app.domain.model.FileRequest

interface FileRepository {

    suspend fun getFile(fileRequest: FileRequest): String
}