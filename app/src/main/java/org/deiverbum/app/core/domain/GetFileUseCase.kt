package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.first
import org.deiverbum.app.core.data.repository.LocalFileRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.FileRequestt
import org.deiverbum.app.core.model.NewFileResponse
import javax.inject.Inject

/**
 * Caso de uso archivos locales.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class GetFileUseCase @Inject constructor(
    private val fileRepository: LocalFileRepository,
    private val userDataRepository: UserDataRepository,

    ) {
    suspend operator fun invoke(fileRequest: FileRequestt): NewFileResponse {
        val user = userDataRepository.userData.first()
        val fileR = fileRepository.getFileModel(fileRequest)
        return NewFileResponse(
            emptyList(), user, fileR
        )
    }

}
