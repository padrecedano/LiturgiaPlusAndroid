package org.deiverbum.app.core.model.book

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LiberUniversalis(var liberFile: LiberFile? = null)

