package org.deiverbum.app.domain.usecase

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.repository.HomilyRepository
import org.deiverbum.app.domain.repository.TodayRepository
import javax.inject.Inject

class GetToday @Inject constructor(
    private val mRepository: TodayRepository
) {
    suspend fun execute(request: TodayRequest): SpannableStringBuilder {
        return mRepository.getToday(request)
    }
}