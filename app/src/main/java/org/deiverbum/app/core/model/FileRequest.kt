package org.deiverbum.app.core.model
/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo File.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class FileRequest(
    var fileName: List<String>,
    var dayOfWeek: Int,
    var timeId: Int,
    var isNightMode: Boolean = false,
    var isVoiceOn: Boolean = true,
    var isBrevis: Boolean
)