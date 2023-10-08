package org.deiverbum.app.core.model

/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class TodayRequest(
    val theDate: Int,
    val typeID: Int,
    val isNightMode: Boolean,
    val isMultipleInvitatory: Boolean
)