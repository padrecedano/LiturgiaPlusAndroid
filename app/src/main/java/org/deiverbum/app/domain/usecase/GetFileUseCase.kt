package org.deiverbum.app.domain.usecase

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.repository.FileRepository
import javax.inject.Inject

/**
 * <p>Caso de uso archivos locales.</p>
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