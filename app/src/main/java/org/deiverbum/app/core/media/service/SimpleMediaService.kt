package org.deiverbum.app.core.media.service


import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.core.media.service.notification.SimpleMediaNotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class SimpleMediaService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    private lateinit var player: Player


    @Inject
    lateinit var notificationManager: SimpleMediaNotificationManager

    companion object {
        const val NOTIFICATION_ID = 123 // Un ID único para tu notificación
        const val NOTIFICATION_CHANNEL_ID = "simple_media_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Media Playback"
    }

    /*
     fun onCreatee() {
        super.onCreate()

       // createNotificationChannel() // Crea el canal si aún no existe

        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player)
            //.setCallback(MyMediaSessionCallback()) // Tu callback personalizado
            .build()

        // Configura el proveedor de notificaciones


        // MediaSessionService intentará llamar a startForeground automáticamente
        // cuando la reproducción comience.
        // Si necesitas iniciar en primer plano inmediatamente al crear el servicio,
        // podrías hacerlo aquí con una notificación básica, pero a menudo no es necesario
        // si la reproducción no comienza de inmediato.
    }

     */
    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession
        )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }


    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession
}