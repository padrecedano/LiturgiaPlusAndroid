package org.deiverbum.app.presentation.home

import org.deiverbum.app.domain.model.Bible

data class HomeItemUiState(
    val title: String,
    val allData: List<Bible>
)