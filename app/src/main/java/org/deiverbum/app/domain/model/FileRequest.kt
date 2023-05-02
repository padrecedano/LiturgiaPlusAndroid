package org.deiverbum.app.domain.model

class FileRequest(
    var fileName: String,
    var dayOfWeek: Int,
    var timeId: Int,
    var isNightMode: Boolean = false,
    var isVoiceOn: Boolean = true,
    var isBrevis: Boolean
)