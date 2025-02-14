package org.deiverbum.app.core.model.data

/**
 * Clase de encapsulación de los parámetros de consulta para [UniversalisResource]
 */
data class UniversalisResourceQuery(
    /**
     * La fecha a buscar.
     */
    var filterDate: Int = 0,
    /**
     * El id del tópico que se busca.
     */
    val filterTopicId: Int = -1,
)