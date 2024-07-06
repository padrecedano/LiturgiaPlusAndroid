package org.deiverbum.app.core.presentation.today

import org.deiverbum.app.core.model.UniversalisResponse
import org.deiverbum.app.core.model.data.Universalis

data class TodayItemUiState(
    val todayResponse: UniversalisResponse,
    val universalis: Universalis
)

