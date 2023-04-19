package org.deiverbum.app.domain.usecase

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.repository.HomilyRepository
import javax.inject.Inject

class GetHomily @Inject constructor(
    private val mRepository: HomilyRepository
) {
    suspend fun execute(request: HomilyRequest): SpannableStringBuilder {
        return mRepository.getHomily(request)
    }
}