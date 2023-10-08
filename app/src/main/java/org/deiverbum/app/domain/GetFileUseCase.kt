package org.deiverbum.app.domain

import org.deiverbum.app.core.data.repository.FileRepository
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse
import javax.inject.Inject

/**
 * Caso de uso archivos locales.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class GetFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun execute(fileRequest: FileRequest): MutableList<FileResponse> {
        return fileRepository.getFile(fileRequest)
    }
}