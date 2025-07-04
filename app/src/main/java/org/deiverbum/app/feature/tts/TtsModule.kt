package org.deiverbum.app.feature.tts

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // El repositorio vivirá mientras la app viva (alcance de Singleton)
abstract class TtsModule {

    @Binds
    @Singleton // Asegura que solo haya una instancia del repositorio en toda la app
    abstract fun bindTtsRepository(
        ttsRepositoryImpl: TtsRepositoryImpl
    ): TtsRepository

}