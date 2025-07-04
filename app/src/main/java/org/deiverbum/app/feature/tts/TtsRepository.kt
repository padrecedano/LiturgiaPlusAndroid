package org.deiverbum.app.feature.tts


import kotlinx.coroutines.flow.StateFlow

/**
 * Interfaz para el repositorio que maneja las operaciones de Text-to-Speech.
 */
interface TtsRepository {
    /**
     * Flujo que emite el estado actual y el progreso de la reproducción TTS.
     */
    val ttsProgress: StateFlow<TtsProgressData>

    /**
     * Prepara el motor TTS y comienza a leer el texto proporcionado.
     * El texto se dividirá internamente en segmentos.
     *
     * @param text El texto completo a leer.
     */
    fun playText(text: String)

    /**
     * Pausa la lectura TTS actual.
     * La reproducción puede reanudarse desde el punto donde se pausó.
     */
    fun pause()

    /**
     * Reanuda la lectura TTS desde el punto donde se pausó.
     * Si no estaba pausado, podría iniciar la reproducción si hay texto cargado.
     */
    fun resume()

    /**
     * Detiene la lectura TTS actual y restablece el progreso.
     * La próxima reproducción comenzará desde el principio del texto.
     */
    fun stop()

    /**
     * Salta a un segmento específico del texto y comienza la reproducción desde allí.
     *
     * @param segmentIndex El índice del segmento al que saltar.
     */
    fun seekToSegment(segmentIndex: Int)

    /**
     * Libera los recursos del motor TTS. Debería llamarse cuando ya no se necesite.
     * Por ejemplo, en onCleared del ViewModel si el servicio no es persistente.
     */
    fun release()

    /**
     * Establece la velocidad del habla.
     * @param rate Velocidad, 1.0 es normal. Valores menores son más lentos, mayores son más rápidos.
     */
    fun setSpeechRate(rate: Float)

    /**
     * Establece el tono del habla.
     * @param pitch Tono, 1.0 es normal. Valores menores son más graves, mayores son más agudos.
     */
    fun setSpeechPitch(pitch: Float)
}