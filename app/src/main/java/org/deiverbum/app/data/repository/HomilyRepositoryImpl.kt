package org.deiverbum.app.data.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.factory.HomilyFactory
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.repository.HomilyRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

class HomilyRepositoryImpl @Inject constructor(
    private val homilyFactory: HomilyFactory
) : HomilyRepository {

    override suspend fun getHomily(homilyRequest: HomilyRequest): SpannableStringBuilder {
        return homilyFactory.create(Source.LOCAL).getHomily(homilyRequest)
            .ifEmpty { syncHomily(homilyRequest) }
    }

    private suspend fun syncHomily(homilyRequest: HomilyRequest): SpannableStringBuilder {
        return homilyFactory.create(Source.NETWORK).getHomily(homilyRequest)
            .also { homilyFromNetwork ->
                homilyFactory.create(Source.LOCAL).addHomily(homilyFromNetwork)
            }
    }
}