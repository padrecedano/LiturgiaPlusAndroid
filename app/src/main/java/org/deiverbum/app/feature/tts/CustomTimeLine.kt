package org.deiverbum.app.feature.tts

import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Timeline
import androidx.media3.common.util.Assertions
import androidx.media3.common.util.UnstableApi

/**
 * Un Timeline simple para representar una lista de segmentos como MediaItems.
 * Cada segmento se trata como un periodo dentro de una única ventana.
 * Las duraciones son desconocidas (C.TIME_UNSET) ya que TTS no las provee fácilmente.
 */
class CustomTimeline @OptIn(UnstableApi::class) constructor
    (private val mediaItems: List<MediaItem>) : Timeline() {

    override fun getWindowCount(): Int = if (mediaItems.isEmpty()) 0 else 1

    @OptIn(UnstableApi::class)
    override fun getWindow(
        windowIndex: Int,
        window: Window,
        defaultPositionProjectionUs: Long
    ): Window {
        Assertions.checkIndex(windowIndex, 0, windowCount - 1) // Asegura que windowIndex sea 0
        val isSeekable = true // Puedes buscar entre segmentos
        val isDynamic =
            false // La lista de segmentos no cambia dinámicamente durante la reproducción de un segmento
        val durationUs = C.TIME_UNSET // La duración total es desconocida
        val defaultPositionUs = 0L
        val firstPeriodIndex = 0
        val lastPeriodIndex = if (mediaItems.isEmpty()) C.INDEX_UNSET else mediaItems.size - 1

        return window.set(
            /* uid = */ Any(), // UID único para la ventana
            /* mediaItem = */
            if (mediaItems.isEmpty()) MediaItem.EMPTY else mediaItems.first(), // MediaItem representativo
            /* manifest = */
            null,
            /* presentationStartTimeMs = */
            C.TIME_UNSET,
            /* windowStartTimeMs = */
            C.TIME_UNSET,
            /* elapsedRealtimeEpochOffsetMs = */
            C.TIME_UNSET,
            /* isSeekable = */
            isSeekable,
            /* isDynamic = */
            isDynamic,
            /* liveConfiguration = */
            null,
            /* defaultPositionUs = */
            defaultPositionUs,
            /* durationUs = */
            durationUs,
            /* firstPeriodIndex = */
            firstPeriodIndex,
            /* lastPeriodIndex = */
            lastPeriodIndex,
            /* positionInFirstPeriodUs = */
            0L
        )
    }

    override fun getPeriodCount(): Int = mediaItems.size

    @OptIn(UnstableApi::class)
    override fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period {
        Assertions.checkIndex(periodIndex, 0, periodCount - 1)
        // Usar el mediaId del MediaItem como ID del periodo si está disponible y setIds es true
        val id = if (setIds) mediaItems[periodIndex].mediaId else null
        // UID único para el periodo si setIds es true
        val uid =
            if (setIds) mediaItems[periodIndex].mediaId ?: Any() else null // O simplemente Any()
        val windowIndex = 0 // Todos los periodos pertenecen a la única ventana
        val durationUs = C.TIME_UNSET // Duración de cada segmento es desconocida
        // La posición en la ventana no es tan relevante para segmentos TTS discretos así
        val positionInWindowUs = 0L

        return period.set(
            /* id= */ id,
            /* uid= */ uid,
            /* windowIndex= */ windowIndex,
            /* durationUs= */ durationUs,
            /* positionInWindowUs= */ positionInWindowUs
        )
    }

    override fun getIndexOfPeriod(uid: Any): Int {
        mediaItems.forEachIndexed { index, mediaItem ->
            if (mediaItem.mediaId == uid) {
                return index
            }
        }
        return C.INDEX_UNSET
    }

    @OptIn(UnstableApi::class)
    override fun getUidOfPeriod(periodIndex: Int): Any {
        Assertions.checkIndex(periodIndex, 0, periodCount - 1)
        return mediaItems[periodIndex].mediaId ?: Any() // Devuelve el mediaId o un objeto único
    }
}