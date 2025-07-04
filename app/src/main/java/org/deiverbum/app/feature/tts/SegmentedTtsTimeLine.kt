package org.deiverbum.app.feature.tts

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi

/**
 * Una implementación de Timeline para representar segmentos de TTS.
 * Cada segmento de texto se trata como un período y una ventana en la timeline.
 *
 * @param mediaItems Lista de MediaItems conceptuales, uno por cada segmento.
 *                   Se usa principalmente para el MediaItem que se reporta en getWindow().
 * @param segmentDurationsMs Lista de duraciones estimadas (o C.TIME_UNSET si son desconocidas)
 *                           para cada segmento, en milisegundos. El orden debe coincidir
 *                           con `mediaItems`.
 */
@UnstableApi
class SegmentedTtsTimeline(
    private val mediaItems: List<MediaItem>,
    private val segmentDurationsMs: List<Long> // Lista de duraciones para cada segmento
) : Timeline() {

    init {
        // Validación básica: debe haber la misma cantidad de mediaItems que de duraciones
        check(mediaItems.size == segmentDurationsMs.size) {
            "El número de MediaItems (${mediaItems.size}) debe coincidir con el número de duraciones (${segmentDurationsMs.size})"
        }
    }

    /**
     * Devuelve el número de ventanas. En este caso, una ventana por segmento.
     */
    override fun getWindowCount(): Int = mediaItems.size

    /**
     * Devuelve el número de períodos. En este caso, un período por segmento.
     * Cada ventana mapea directamente a un período.
     */
    override fun getPeriodCount(): Int = mediaItems.size

    /**
     * Obtiene los datos de una ventana específica.
     */
    override fun getWindow(
        windowIndex: Int,
        window: Window,
        defaultPositionProjectionUs: Long
    ): Window {
        checkIndex(windowIndex, 0, windowCount - 1)

        val durationUs = if (segmentDurationsMs[windowIndex] == C.TIME_UNSET) {
            C.TIME_UNSET
        } else {
            C.msToUs(segmentDurationsMs[windowIndex])
        }

        val mediaItemForWindow = mediaItems[windowIndex]

        return window.set(
            /* uid = */ windowIndex, // UID simple para la ventana, podría ser más robusto
            /* mediaItem = */
            mediaItemForWindow,
            /* manifest = */
            null, // No hay manifiesto para TTS
            /* presentationStartTimeMs = */
            C.TIME_UNSET, // No relevante para TTS simple
            /* windowStartTimeMs = */
            C.TIME_UNSET,       // No relevante para TTS simple
            /* elapsedRealtimeEpochOffsetMs = */
            C.TIME_UNSET, // No relevante
            /* isSeekable = */
            true, // Puedes buscar entre segmentos
            /* isDynamic = */
            durationUs == C.TIME_UNSET, // Es dinámico si la duración aún no se conoce
            /* liveConfiguration = */
            null, // No es contenido en vivo
            /* defaultPositionUs = */
            0L, // Comienza al inicio del segmento
            /* durationUs = */
            durationUs,
            /* firstPeriodIndex = */
            windowIndex, // Cada ventana mapea a su propio período
            /* lastPeriodIndex = */
            windowIndex,  // Cada ventana mapea a su propio período
            /* positionInFirstPeriodUs = */
            0L
        )
    }

    /**
     * Obtiene los datos de un período específico.
     */
    override fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period {
        checkIndex(periodIndex, 0, periodCount - 1)

        val id = if (setIds) periodIndex else null // UID simple para el período
        val uid = if (setIds) periodIndex else null

        val durationUs = if (segmentDurationsMs[periodIndex] == C.TIME_UNSET) {
            C.TIME_UNSET
        } else {
            C.msToUs(segmentDurationsMs[periodIndex])
        }
        // La posición en la ventana es 0 porque cada período es su propia ventana aquí.
        val positionInWindowUs = 0L

        return period.set(
            /* id = */ id,
            /* uid = */ uid,
            /* windowIndex = */ periodIndex, // Cada período mapea a su propia ventana
            /* durationUs = */ durationUs,
            /* positionInWindowUs = */ positionInWindowUs
            // adPlaybackStates no es relevante para TTS simple
        )
    }

    /**
     * Devuelve el índice del período que contiene el UID dado.
     * Usamos el índice como UID aquí por simplicidad.
     */
    override fun getIndexOfPeriod(uid: Any): Int {
        return if (uid is Int && uid >= 0 && uid < periodCount) {
            uid
        } else {
            C.INDEX_UNSET
        }
    }

    /**
     * Devuelve el UID del período en el índice dado.
     * Usamos el índice como UID aquí por simplicidad.
     */
    override fun getUidOfPeriod(periodIndex: Int): Any {
        checkIndex(periodIndex, 0, periodCount - 1)
        return periodIndex
    }

    private fun checkIndex(index: Int, min: Int, max: Int) {
        if (index < min || index > max) {
            throw IndexOutOfBoundsException()
        }
    }
}