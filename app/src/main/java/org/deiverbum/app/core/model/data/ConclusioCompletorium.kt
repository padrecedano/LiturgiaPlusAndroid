package org.deiverbum.app.core.model.data

/**
 * Representa la conclusión de [LHCompletorium] para la capa de datos externa.
 * @property VirginAntiphon La Antífona de la Virgen.
 *
 * @since 2024.1
 *
 */

data class ConclusioCompletorium(val antiphon: VirginAntiphon) {
    val benedictio =
        listOf("El Señor todopoderoso nos conceda una noche tranquila y una santa muerte.", "Amén.")
//TODO para forRead crear un método en Utils
}