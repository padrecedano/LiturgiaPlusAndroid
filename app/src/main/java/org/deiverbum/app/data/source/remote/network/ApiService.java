package org.deiverbum.app.data.source.remote.network;


import org.deiverbum.app.data.wrappers.Crud;
import org.deiverbum.app.data.wrappers.SyncRequest;
import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReadingList;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.Visperas;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */



public interface ApiService {

    @GET("comentarios/{theDate}")
    Single<BibleCommentList> getComentarios(@Path("theDate") String theDate);

    @GET("{endPoint}/{dateString}")
    Single<BreviaryHour> getBreviario(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

    @GET("homilias/{theDate}")
    Single<Homily> getHomilias(@Path("theDate") String theDate);

    @GET("lecturas/{theDate}")
    Single<MassReadingList> getLecturas(@Path("theDate") String theDate);

    @GET("{endPoint}/{dateString}")
    Single<Liturgy> getBreviary(@Path("endPoint") String endPoint, @Path("dateString") String dateString);


    @GET("mixto/{theDate}")
    Single<Mixto> getMixto(@Path("theDate") String cleanDate);

    @GET("oficio/{theDate}")
    Single<Oficio> getOficio(@Path("theDate") String cleanDate);

    @GET("laudes/{theDate}")
    Single<Laudes> getLaudes(@Path("theDate") String cleanDate);

    //@Headers("Cache-Control: no-cache")
    @GET("{endPoint}/{dateString}")
    Single<Intermedia> getIntermedia(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

    @GET("visperas/{theDate}")
    Single<Visperas> getVisperas(@Path("theDate") String cleanDate);

    @GET("metaliturgia/{theDate}")
    Single<MetaLiturgia> getMetaLiturgia(@Path("theDate") String cleanDate);

    @Headers("Cache-Control: no-cache")
    @GET("test/{theDate}")
    Single<List<Today>> getToday(@Path("theDate") String cleanDate);

    @Headers("Cache-Control: no-cache")
    @GET("sync/{theId}")
    Single<List<Liturgy>> getLiturgia(@Path("theId") Integer theId);

    @Headers("Cache-Control: no-cache")
    @GET("cruds/{theId}")
    Single<Crud> getCruds(@Path("theId") Integer theId);

    @Headers("Cache-Control: no-cache")
    //@FormUrlEncoded
    @GET("crud/")
    Single<Crud> getCrud(@QueryMap Map<String,Integer> map);

    @Headers("Cache-Control: no-cache")
    //@FormUrlEncoded
    @GET("update/")
    Single<Crud> callUpdate(@QueryMap Map<String,Integer> map);

    @Headers("Cache-Control: no-cache")
    //@FormUrlEncoded
    @GET("insertt/")
    Single<Crud> callInsert(@QueryMap Map<String,Integer> map);


    @Headers("Cache-Control: no-cache")
    //@FormUrlEncoded
    @GET("delete/")
    Single<Crud> callDelete(@QueryMap Map<String,Integer> map);

    @Headers("Cache-Control: no-cache")
    @POST("delete/")
    Single<Crud> callDelete(@Body SyncRequest syncRequest);

    @Headers("Cache-Control: no-cache")
    @POST("insert/")
    Single<Crud> callInsertB(@Body SyncRequest syncRequest);
    //Completable callInsertB(List<SyncStatus> syncStatus);
}
