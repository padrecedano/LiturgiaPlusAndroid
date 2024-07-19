package org.deiverbum.app.core.network.di

import android.content.Context
import androidx.tracing.trace
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deiverbum.app.BuildConfig
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.LiturgiaTypus
import org.deiverbum.app.core.model.data.Missae
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(LiturgiaTypus::class.java, "typus")
                .withSubtype(LHMixtus::class.java, "mixtus")
                .withSubtype(LHOfficium::class.java, "officium")
                .withSubtype(LHLaudes::class.java, "laudes")
                .withSubtype(LHIntermedia::class.java, "intermedia")
                .withSubtype(LHIntermedia::class.java, "tertiam")
                .withSubtype(LHIntermedia::class.java, "sextam")
                .withSubtype(LHIntermedia::class.java, "nonam")
                .withSubtype(LHVesperas::class.java, "vesperas")
                .withSubtype(LHCompletorium::class.java, "completorium")
                .withSubtype(Missae::class.java, "lectionum")
                .withSubtype(Missae::class.java, "homiliae")
                .withSubtype(Commentarii::class.java, "commentarii")
                .withSubtype(Alteri.Sancti::class.java, "sanctii")
            //.withDefaultValue(LiturgiaTypus.Unknown)
        )
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesNetworkJson(): Moshi = moshi

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()


    /**
     * Since we're displaying SVGs in the app, Coil needs an ImageLoader which supports this
     * format. During Coil's initialization it will call `applicationContext.newImageLoader()` to
     * obtain an ImageLoader.
     *
     * @see <a href="https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt">Coil</a>
     */
    @Provides
    @Singleton
    fun imageLoader(
        // We specifically request dagger.Lazy here, so that it's not instantiated from Dagger.
        okHttpCallFactory: dagger.Lazy<Call.Factory>,
        @ApplicationContext application: Context,
    ): ImageLoader = trace("NiaImageLoader") {
        ImageLoader.Builder(application)
            .callFactory { okHttpCallFactory.get() }
            .components { add(SvgDecoder.Factory()) }
            // Assume most content images are versioned urls
            // but some problematic images are fetching each time
            .respectCacheHeaders(false)
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .build()
    }
}