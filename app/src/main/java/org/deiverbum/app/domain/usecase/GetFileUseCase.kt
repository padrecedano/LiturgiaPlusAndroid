package org.deiverbum.app.domain.usecase

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.repository.FileRepository
import javax.inject.Inject

class GetFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun execute(fileRequest: FileRequest): FileResponse {
        return fileRepository.getFile(fileRequest)
    }
}