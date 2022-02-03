package org.deiverbum.app.data.source.remote.network;
import org.deiverbum.app.model.BreviarioHora;
import org.deiverbum.app.model.Comentario;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.HomiliaCompleta;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Todo;
import org.deiverbum.app.model.Visperas;

//import io.reactivex.rxjava3.core.Single;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import io.reactivex.rxjava3.core.Single;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */



public interface ApiService {

    @Headers("Cache-control: no-cache")
    @GET("comentarios/{theDate}")
    Single<Comentarios> getComentarios(@Path("theDate") String theDate);

    @GET("{endPoint}/{dateString}")
    Single<BreviarioHora> getBreviario(@Path("endPoint") String endPoint,@Path("dateString") String dateString);


    @GET("homilias/{theDate}")
    Single<Homilias> getHomilias(@Path("theDate") String theDate);

    @GET("lecturas/{theDate}")
    Single<Lecturas> getLecturas(@Path("theDate") String theDate);

    @Headers("Cache-control: no-cache")
    @GET("mixto/{theDate}")
    Single<Mixto> getMixto(@Path("theDate") String cleanDate);

    @Headers("Cache-control: no-cache")
    @GET("oficio/{theDate}")
    Single<Oficio> getOficio(@Path("theDate") String cleanDate);

    @Headers("Cache-control: no-cache")
    @GET("laudes/{theDate}")
    Single<Laudes> getLaudes(@Path("theDate") String cleanDate);

    @Headers("Cache-control: no-cache")
    @GET("{endPoint}/{dateString}")
    Single<Intermedia> getIntermedia(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

    @Headers("Cache-control: no-cache")
    @GET("visperas/{theDate}")
    Single<Visperas> getVisperas(@Path("theDate") String cleanDate);

    @Headers("Cache-control: no-cache")
    @GET("metaliturgia/{theDate}")
    Single<MetaLiturgia> getMetaLiturgia(@Path("theDate") String cleanDate);
}
