package org.deiverbum.app.domain.model


/**
 * Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 *
 * @param hasInitial indica si en las preferencias ya hay registrada una sincronización inicial.
 * @param yearToClean se pasará un dato distinto de `0` cuando se necesite limpiar datos del año anterior.

 */
class SyncRequest(
    var hasInitial: Boolean,
    var yearToClean: Int =0,
    var isWorkScheduled: Boolean
)