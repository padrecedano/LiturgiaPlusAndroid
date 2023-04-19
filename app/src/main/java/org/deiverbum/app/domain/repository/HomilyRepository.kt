package org.deiverbum.app.domain.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest

interface HomilyRepository {

    suspend fun getHomily(homilyRequest: HomilyRequest): SpannableStringBuilder

}