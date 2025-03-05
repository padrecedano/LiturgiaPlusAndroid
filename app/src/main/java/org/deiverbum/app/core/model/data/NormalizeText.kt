package org.deiverbum.app.core.model.data

/**
 * Para manejar los datos normalizables dinámicos.
 *
 * @property text El texto normalizado.
 * @property isNormalizable Será `true` cuando el texto sea normalizable.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 * @see [LHPsalm]
 */
data class NormalizeText(
    val text: String,
    val isNormalizable: Boolean = false
)