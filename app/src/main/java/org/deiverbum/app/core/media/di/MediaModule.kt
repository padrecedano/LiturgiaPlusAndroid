package org.deiverbum.app.core.media.di

import android.content.Context
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.media.service.TtsServiceHandler
import org.deiverbum.app.core.media.service.notification.TtsNotificationManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @Provides
    @Singleton
    @UnstableApi
    fun providePlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()


    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): MediaSession =
        MediaSession.Builder(context, player)
            .build()


    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideTtsNotificationManager(
        @ApplicationContext context: Context,
        player: ExoPlayer

    ): TtsNotificationManager =
        TtsNotificationManager(
            context = context,
            player = player
        )

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideTtsServiceHandler(
        @ApplicationContext context: Context,

        //player: TtsPlayer,
    ): TtsServiceHandler =
        TtsServiceHandler(
            //player = player,
            context = context
        )

}