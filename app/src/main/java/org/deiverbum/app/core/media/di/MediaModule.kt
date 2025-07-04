package org.deiverbum.app.core.media.di

import android.content.Context
import android.os.Build
import android.os.Looper
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
import org.deiverbum.app.feature.tts.MinimalTtsPlayer
import org.deiverbum.app.feature.tts.TtsPlayerOld
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideTtsPlayer(
        @ApplicationContext context: Context,
        @Named("MainLooper") mainLooper: Looper
    ): TtsPlayerOld {
        // Asumiendo la Opción 3 de refactorización para TtsPlayerForApi26
        return TtsPlayerOld(mainLooper, context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideMinimalTtsPlayer(
        @ApplicationContext context: Context,
        @Named("MainLooper") mainLooper: Looper
    ): MinimalTtsPlayer {
        // Asumiendo la Opción 3 de refactorización para TtsPlayerForApi26
        return MinimalTtsPlayer(context, mainLooper)
    }

    @Provides
    @Singleton
    @Named("MainLooper")
    fun provideMainLooper(): Looper {
        return Looper.getMainLooper()
    }

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


    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        //player: ExoPlayer,
        player: TtsPlayerOld

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
        //player: TtsPlayer


    ): TtsNotificationManager =
        TtsNotificationManager(
            context = context,
            player = player
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideTtsServiceHandler(
        @ApplicationContext context: Context,
        player: TtsPlayerOld,
        //player: ExoPlayer,
    ): TtsServiceHandler =
        TtsServiceHandler(
            ttsPlayer = player,
            context = context
        )

}