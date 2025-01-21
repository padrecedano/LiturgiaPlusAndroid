package org.deiverbum.app.core.media.service.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager

@UnstableApi
class TtsMediaNotificationAdapter(
    private val context: Context,
    private val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {

    override fun getCurrentContentTitle(player: Player): CharSequence =
        player.mediaMetadata.albumTitle ?: ""

    override fun createCurrentContentIntent(player: Player): PendingIntent? =
        pendingIntent

    override fun getCurrentContentText(player: Player): CharSequence =
        player.mediaMetadata.displayTitle ?: ""

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return null
    }
}