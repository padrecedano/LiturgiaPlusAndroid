package org.deiverbum.app.domain.model
/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo File.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class FileRequest(
    var fileName: String,
    var dayOfWeek: Int,
    var timeId: Int,
    var isNightMode: Boolean = false,
    var isVoiceOn: Boolean = true,
    var isBrevis: Boolean
)