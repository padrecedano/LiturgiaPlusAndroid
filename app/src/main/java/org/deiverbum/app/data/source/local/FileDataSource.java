package org.deiverbum.app.data.source.local;

import com.google.gson.Gson;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.model.OracionSimple;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.ViaCrucis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


/**
 * <p>Esta clase hace las peticiones a los datos que se encuentran en archivos locales.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */

public class FileDataSource {

    @Inject
    public FileDataSource() {
    }


    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.Rosario} envuelto en {@link DataWrapper}.</p>
     * @param day El día para determinar el {@link org.deiverbum.app.model.Misterio} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */

    public Single<DataWrapper<Rosario, CustomException>> getRosario(int day) {
        return Single.create(emitter -> {
            try {
                String filePath = "res/raw/rosario.json";
                InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(filePath);
                Gson gson = new Gson();
                Rosario rosario = gson.fromJson(new InputStreamReader(raw), Rosario.class);
                rosario.setDay(day);
                emitter.onSuccess(new DataWrapper<>(rosario));
            }catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.OracionSimple} envuelto en {@link DataWrapper}.</p>
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.OracionSimple} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    public Single<DataWrapper<OracionSimple, CustomException>> getOracionSimple(String rawPath) {
        return Single.create(emitter -> {
            try {
                InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(rawPath);
                Gson gson = new Gson();
                OracionSimple data = gson.fromJson(new InputStreamReader(raw), OracionSimple.class);
                emitter.onSuccess(new DataWrapper<>(data));
            }catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }

    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.ViaCrucis} envuelto en {@link DataWrapper}.</p>
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.ViaCrucis} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    public Single<DataWrapper<ViaCrucis, CustomException>> getViaCrucis(String rawPath) {
        return Single.create(emitter -> {
            try {
                InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(rawPath);
                Gson gson = new Gson();
                ViaCrucis data = gson.fromJson(new InputStreamReader(raw), ViaCrucis.class);
                emitter.onSuccess(new DataWrapper<>(data));
            }catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }


    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.OracionSimple} envuelto en {@link DataWrapper}.</p>
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.OracionSimple} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    public Single<DataWrapper<Book, CustomException>> getBook(String rawPath) {
        return Single.create(emitter -> {
            try {
                InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(rawPath);
                Gson gson = new Gson();
                Book data = gson.fromJson(new InputStreamReader(raw)
                        , Book.class);
                emitter.onSuccess(new DataWrapper<>(data));
            }catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }
}
