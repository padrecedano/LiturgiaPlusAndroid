package org.deiverbum.app.core.model.data
/**
 * <p>Recoge los parámetros que son enviados a las peticiones para el módulo File.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
class FileRequest(
    var fileName: List<String>,
    var dayOfWeek: Int,
    var timeId: Int,
    var isNightMode: Boolean = false,
    var isVoiceOn: Boolean = true,
    var isBrevis: Boolean
)

class FileRequestt(
    var fileName: List<FileItem>,
    var dayOfWeek: Int,
    var timeId: Int,
    var isNightMode: Boolean = false,
    var isVoiceOn: Boolean = true,
    var isBrevis: Boolean
)

data class FileItem(var fileName: String, var fileTitle: String)