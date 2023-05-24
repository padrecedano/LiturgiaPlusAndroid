package org.deiverbum.app.domain.model


/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 *
 * @param isInitial indica si el tipo de sincronización es inicial o parcial.
 * @param yearToClean se pasará un dato distinto de `0` cuando se necesite limpiar datos del año anterior.

 */
class SyncRequest(
    val isInitial: Boolean,
    val yearToClean: Int =0
)