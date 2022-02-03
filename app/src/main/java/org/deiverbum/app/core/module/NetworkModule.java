package org.deiverbum.app.core.module;

import static org.deiverbum.app.utils.Configuration.URL_API;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.CacheControl;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import android.app.Application;

import org.deiverbum.app.core.utils.ConnectivityIntecepter;
import org.deiverbum.app.data.source.remote.network.ApiService;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */

@Module
//@InstallIn(BaseApplication.class)
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
        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .dispatcher(dispatcher);
        //TODO revisar
            /*
        if (BuildConfig.DEBUG){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addNetworkInterceptor(interceptor);
        client.addInterceptor(new ChuckInterceptor(application));
        }
        */

        client.addInterceptor(new ConnectivityIntecepter(application));
        Interceptor interceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        client.addInterceptor(interceptor);
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

