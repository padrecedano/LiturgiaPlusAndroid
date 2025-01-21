package org.deiverbum.app.core.media.di

import android.content.Context
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.media.service.SimpleMediaServiceHandler
import org.deiverbum.app.core.media.service.notification.SimpleMediaNotificationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SimpleMediaModule {
    /*
        @Provides
        @Singleton
        fun provideAudioAttributes(): AudioAttributes =
            AudioAttributes.Builder()
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                .setUsage(C.USAGE_MEDIA)
                .build()

        @Provides
        @Singleton
        @UnstableApi
        fun providePlayer(
            @ApplicationContext context: Context,
            audioAttributes: AudioAttributes
        ): ExoPlayer =
            ExoPlayer.Builder(context)
                .setAudioAttributes(audioAttributes, true)
                .setHandleAudioBecomingNoisy(true)
                .setTrackSelector(DefaultTrackSelector(context))
                .build()

        @Provides
        @Singleton
        fun provideMediaSession(
            @ApplicationContext context: Context,
            player: ExoPlayer
        ): MediaSession =
            MediaSession.Builder(context, player).build()
    */
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        player: ExoPlayer
    ): SimpleMediaNotificationManager =
        SimpleMediaNotificationManager(
            context = context,
            player = player
        )


    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideServiceHandler(
        player: ExoPlayer,
    ): SimpleMediaServiceHandler =
        SimpleMediaServiceHandler(
            player = player,
            // context=context
        )


}