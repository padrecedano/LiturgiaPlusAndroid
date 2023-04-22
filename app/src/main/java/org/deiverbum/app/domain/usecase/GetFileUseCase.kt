package org.deiverbum.app.domain.usecase

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.repository.FileRepository
import javax.inject.Inject

class GetFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun execute(request: FileRequest): String {
        return fileRepository.getFile(request)
    }
}