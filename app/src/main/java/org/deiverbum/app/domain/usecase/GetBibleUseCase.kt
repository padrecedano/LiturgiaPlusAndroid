package org.deiverbum.app.domain.usecase

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import org.deiverbum.app.domain.repository.BibleRepository
import javax.inject.Inject

class GetBibleUseCase @Inject constructor(
    private val bibleRepository: BibleRepository
) {

    suspend fun execute(request: BibleRequest): List<Bible> {
        return bibleRepository.getBible(request)
    }
}