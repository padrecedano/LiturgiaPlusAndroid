package org.deiverbum.app.data.source.remote.network;

import org.deiverbum.app.model.BreviarioHora;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.Visperas;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */



public interface ApiService {

    @GET("comentarios/{theDate}")
    Single<Comentarios> getComentarios(@Path("theDate") String theDate);

    @GET("{endPoint}/{dateString}")
    Single<BreviarioHora> getBreviario(@Path("endPoint") String endPoint,@Path("dateString") String dateString);

    @GET("homilias/{theDate}")
    Single<Homilias> getHomilias(@Path("theDate") String theDate);

    @Headers("Cache-Control: no-cache")
    @GET("lecturas/{theDate}")
    Single<Lecturas> getLecturas(@Path("theDate") String theDate);

    @Headers("Cache-Control: no-cache")
    @GET("mixto/{theDate}")
    Single<Mixto> getMixto(@Path("theDate") String cleanDate);

    @Headers("Cache-Control: no-cache")
    @GET("oficio/{theDate}")
    Single<Oficio> getOficio(@Path("theDate") String cleanDate);

    //@Headers("Cache-Control: no-cache")
    @GET("laudes/{theDate}")
    Single<Laudes> getLaudes(@Path("theDate") String cleanDate);

    //@Headers("Cache-Control: no-cache")
    @GET("{endPoint}/{dateString}")
    Single<Intermedia> getIntermedia(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

    @GET("visperas/{theDate}")
    Single<Visperas> getVisperas(@Path("theDate") String cleanDate);

    @GET("metaliturgia/{theDate}")
    Single<MetaLiturgia> getMetaLiturgia(@Path("theDate") String cleanDate);

    @GET("test/{theDate}")
    Single<List<Today>> getToday(@Path("theDate") String cleanDate);
}
