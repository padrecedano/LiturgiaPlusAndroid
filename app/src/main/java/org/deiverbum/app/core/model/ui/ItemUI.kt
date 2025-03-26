package org.deiverbum.app.core.model.data.ui

import kotlinx.serialization.Serializable
import org.deiverbum.app.util.DateTimeUtil

/**
 * Representa un item de la UI.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
@Serializable
data class ItemUI(
    val id: Int,
    val title: String,
    val group: Int,
    var currentDate: Int = DateTimeUtil.getTodayDate()
)
