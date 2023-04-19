package org.deiverbum.app.data.source

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest

interface HomilyEntityData {

    suspend fun getHomily(homilyRequest: HomilyRequest): SpannableStringBuilder

    suspend fun addHomily(homily: SpannableStringBuilder)
}