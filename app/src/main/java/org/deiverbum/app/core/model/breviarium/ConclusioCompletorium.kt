package org.deiverbum.app.core.model.data.breviarium

import org.deiverbum.app.core.model.breviarium.VirginAntiphon

/**
 * Representa la conclusión de [BreviariumCompletorium] para la capa de datos externa.
 * @property VirginAntiphon La Antífona de la Virgen.
 *
 * @since 2025.1
 *
 */

data class ConclusioCompletorium(val antiphon: VirginAntiphon) {
    val benedictio =
        listOf("El Señor todopoderoso nos conceda una noche tranquila y una santa muerte.", "Amén.")
}