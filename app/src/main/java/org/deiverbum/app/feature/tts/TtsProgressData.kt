package org.deiverbum.app.feature.tts

/**
 * Contiene la información sobre el progreso actual de la lectura TTS.
 *
 * @param currentSegment Índice base 0 del segmento de texto que se está reproduciendo o está en pausa.
 * @param totalSegments Número total de segmentos en los que se ha dividido el texto.
 * @param currentSegmentText El texto del segmento actual (útil para mostrar en UI).
 * @param playbackState El estado actual de la reproducción.
 * @param error Mensaje de error si el estado es ERROR.
 * @param isBuffering Indica si el TTS está actualmente cargando/preparando.
 */
data class TtsProgressData(
    val playbackState: TtsPlaybackState = TtsPlaybackState.IDLE,
    val currentSegment: Int = 0,
    val totalSegments: Int = 0,
    val currentSegmentText: String = "",
    val isBuffering: Boolean = false,
    val error: String? = null,
    // --- AÑADIR ESTOS CAMPOS ---
    val speechRate: Float = 1.0f,   // Valor por defecto, ej. velocidad normal
    val speechPitch: Float = 1.0f   // Valor por defecto, ej. tono normal
)