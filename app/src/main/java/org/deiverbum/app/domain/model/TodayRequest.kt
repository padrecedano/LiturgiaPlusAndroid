package org.deiverbum.app.domain.model

/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class TodayRequest(
    val theDate: Int?,
    val typeID: Int,
    val isNightMode: Boolean,
    val isVoiceOn: Boolean,
    val isMultipleInvitatory: Boolean
)