package org.deiverbum.app.domain.model


/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class SyncRequest(
    val theDate: Int?,
    val type: Int, //1=initial
    val isNightMode: Boolean,
    val isVoiceOn: Boolean
)