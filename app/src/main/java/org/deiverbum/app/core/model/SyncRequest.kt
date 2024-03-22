package org.deiverbum.app.core.model


/**
 * Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2024.1
 *
 * @param hasInitialSync indica si en las preferencias ya hay registrada una sincronización inicial.
 * @param yearToClean se pasará un dato distinto de `0` cuando se necesite limpiar datos del año anterior.

 */
class SyncRequest(
    var hasInitialSync: Boolean,
    var yearToClean: Int =0,
    var isWorkScheduled: Boolean
)