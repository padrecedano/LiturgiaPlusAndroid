package org.deiverbum.app.core.module;

import static org.deiverbum.app.utils.Configuration.URL_API;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.deiverbum.app.core.utils.ConnectivityIntecepter;
import org.deiverbum.app.data.source.remote.network.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {


    @Provides
    @Singleton
    static Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                                    RxJava3CallAdapterFactory rxJava3CallAdapterFactory,
                                    OkHttpClient okHttpClient
    ) {


        return new Retrofit.Builder().baseUrl(URL_API)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava3CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    static OkHttpClient provideHttpClient(Application application) {
        Dispatcher dispatcher = new Dispatcher();
        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .dispatcher(dispatcher);

        client.addInterceptor(new ConnectivityIntecepter(application));
        Interceptor interceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        client.addInterceptor(interceptor);
        //client.interceptors().add(logging);
        return client.build();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    static GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    static RxJava3CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    static ApiService provideService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}

