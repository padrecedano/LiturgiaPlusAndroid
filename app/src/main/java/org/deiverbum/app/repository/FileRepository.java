package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;
import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHelper;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Esta clase obtiene el contenido guardado en archivos para mostrarlo en pantalla.</p>
 * Created by A. Cedano on 2021/11/11
 * @since 2021.01
 */
public class FileRepository {
    private  final MutableLiveData<String> mText;
    private final FileDataSource fileDataSource;
    private final Context mContext;


    @Inject
    public FileRepository(@ApplicationContext Context context,FileDataSource fileDataSource) {
        mText = new MutableLiveData<>();
        this.fileDataSource = fileDataSource;
        this.mContext = context;
    }

    public LiveData<DataWrapper<Book, CustomException>> getBook(String rawPath) {
        MutableLiveData<DataWrapper<Book, CustomException>> finalData =
                new MediatorLiveData<>();
        fileDataSource.getBook(rawPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Book,
                        CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<Book,
                            CustomException> data) {
                        finalData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finalData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s", e.getMessage(), ERR_REPORT))));
                        dispose();
                    }});

        return finalData;
    }
}

