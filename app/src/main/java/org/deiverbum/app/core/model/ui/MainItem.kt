package org.deiverbum.app.core.model.data.ui

/**
 * Agrupa todos los items de la UI.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
data class MainItem(
    var parent: String,
    var childs: List<ChildItem>
)
