package org.deiverbum.app.data.source.network;

import org.deiverbum.app.model.Today;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */


public interface ApiService {

    @GET("{endPoint}/{dateString}")
    Single<Today> getToday(@Path("endPoint") String endPoint, @Path("dateString") String dateString);

}
