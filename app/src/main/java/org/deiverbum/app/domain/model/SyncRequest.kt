package org.deiverbum.app.domain.model


/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class SyncRequest(
    val theDate: Int?,
    val typeID: Int,
    val isNightMode: Boolean,
    val isVoiceOn: Boolean
)