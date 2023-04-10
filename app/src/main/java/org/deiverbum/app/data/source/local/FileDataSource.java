package org.deiverbum.app.data.source.local;

import static org.deiverbum.app.utils.Constants.ERR_FILE_NOT_FOUND;
import static org.deiverbum.app.utils.Constants.FILE_NIGHT_PRAYER;
import static org.deiverbum.app.utils.Constants.FILE_ROSARY;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.OracionSimple;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.ViaCrucis;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Single;


/**
 * <p>Esta clase hace las peticiones a los datos que se encuentran en archivos locales.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */

public class FileDataSource {

    private final Context mContext;

    @Inject
    public FileDataSource(@ApplicationContext Context context) {
        this.mContext = context;
    }


    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.Rosario} envuelto en {@link DataWrapper}.</p>
     *
     * @param day El día para determinar el {@link org.deiverbum.app.model.Misterio} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Single<DataWrapper<Rosario, CustomException>> getRosario(int day) {
        return Single.create(emitter -> {
            try {
                AssetManager manager = mContext.getAssets();
                InputStream is = manager.open(FILE_ROSARY);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                Gson gson = new Gson();
                Rosario data = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), Rosario.class);
                data.setDay(day);
                emitter.onSuccess(new DataWrapper<>(data));
            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.OracionSimple} envuelto en {@link DataWrapper}.</p>
     *
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.OracionSimple} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Single<DataWrapper<OracionSimple, CustomException>> getOracionSimple(String rawPath) {
        return Single.create(emitter -> {
            try {
                try {
                    AssetManager manager = mContext.getAssets();
                    InputStream is = manager.open(rawPath);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    Gson gson = new Gson();
                    OracionSimple data = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), OracionSimple.class);
                    emitter.onSuccess(new DataWrapper<>(data));
                } catch (Exception e) {
                    emitter.onError(new Exception(ERR_FILE_NOT_FOUND));
                }
            } catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }

    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.ViaCrucis} envuelto en {@link DataWrapper}.</p>
     *
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.ViaCrucis} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Single<DataWrapper<ViaCrucis, CustomException>> getViaCrucis(String rawPath) {
        return Single.create(emitter -> {
            try {
                AssetManager manager = mContext.getAssets();
                InputStream is = manager.open(rawPath);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                Gson gson = new Gson();
                ViaCrucis data = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), ViaCrucis.class);
                emitter.onSuccess(new DataWrapper<>(data));
            } catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }


    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.OracionSimple} envuelto en {@link DataWrapper}.</p>
     *
     * @param rawPath La ruta del archivo que tiene los datos de la {@link org.deiverbum.app.model.OracionSimple} correspondiente
     * @return Observable del tipo solicitado o error.
     * @since 2022.01.01
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Single<DataWrapper<Book, CustomException>> getBook(String rawPath) {
        return Single.create(emitter -> {
            try {
                AssetManager manager = mContext.getAssets();
                InputStream is = manager.open(rawPath);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                //mText.setValue(new String(buffer, StandardCharsets.UTF_8));
                Gson gson = new Gson();
                Book data = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), Book.class);
                emitter.onSuccess(new DataWrapper<>(data));
            } catch (Exception e) {
                emitter.onError(new Exception(e));
            }

        });
    }

    /**
     * <p>Obtiene un observable de {@link org.deiverbum.app.model.Today} envuelto en {@link DataWrapper}.</p>
     *
     * @param today Un objeto {@link org.deiverbum.app.model.Today} con información previa, al cual se adjuntarán las completas.
     * @return Observable del tipo solicitado o error.
     * @since 2023.1
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Single<DataWrapper<Today, CustomException>> getCompletas(Today today) {
        return Single.create(emitter -> {
            try {
                AssetManager manager = mContext.getAssets();
                InputStream is = manager.open(FILE_NIGHT_PRAYER);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                Gson gson = new Gson();
                Completas hora = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), Completas.class);
                hora.setTypeId(7);
                hora.setToday(today);
                BreviaryHour bh = new BreviaryHour();
                bh.setTypeId(7);
                bh.setCompletas(hora);
                hora.setBreviaryHour(bh);
                Liturgy l = today.liturgyDay;
                l.setBreviaryHour(bh);
                emitter.onSuccess(new DataWrapper<>(today));
            } catch (Exception e) {
                emitter.onError(new Exception(e));
            }
        });
    }
}
