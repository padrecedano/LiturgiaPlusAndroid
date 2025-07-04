package org.deiverbum.app.feature.tts

/**
 * Define los posibles estados del reproductor TTS.
 */
enum class TtsPlaybackState {
    IDLE,       // No hay nada que reproducir o está detenido/inicializado
    BUFFERING,  // Inicializando TTS o preparando el texto para hablar
    PLAYING,    // Hablando activamente
    PAUSED,     // Simulación de pausa (TTS detenido, pero se sabe dónde continuar)
    STOPPED,    // Detenido explícitamente por el usuario, se reinicia la posición
    COMPLETED,  // Toda la lectura ha finalizado
    ERROR       // Error en TTS
}

