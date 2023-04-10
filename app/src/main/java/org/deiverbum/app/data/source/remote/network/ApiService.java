package org.deiverbum.app.data.source.remote.network;

import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.MassReadingList;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.crud.Crud;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Single<Today> getToday(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

    @POST("crud/")
    Single<Crud> postCrud(@Body SyncStatus syncStatus);

    @GET("today/{thePath}")
    Single<List<Today>> getTodayAll(@Path("thePath") String thePath);
}
