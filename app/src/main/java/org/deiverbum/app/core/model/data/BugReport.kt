package org.deiverbum.app.core.model.data

data class BugReport(
    val message: String,
    val bugItems: List<BugItem>,
)