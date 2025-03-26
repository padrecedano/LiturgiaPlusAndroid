package org.deiverbum.app.core.model.data

import org.deiverbum.app.core.model.data.ui.ItemUI

/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
class TodayRequest(
    val theDate: Int,
    val typeID: Int,
    val isNightMode: Boolean,
    val isMultipleInvitatory: Boolean
)

data class TopicRequest(val topic: ItemUI, val currentDate: Int)