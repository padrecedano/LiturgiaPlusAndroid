package org.deiverbum.app.feature.tts

data class TTSState(
    val text: String = "",
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isStopped: Boolean = true,
    val error: String? = null,
    val progress: Float = 0f
)