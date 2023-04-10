package org.deiverbum.app.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Esta clase obtiene el contenido guardado en archivos para mostrarlo en pantalla.</p>
 * Created by A. Cedano on 2021/11/11
 *
 * @since 2021.01
 */
public class FileRepository {
    private final FileDataSource fileDataSource;

    @Inject
    public FileRepository(
            FileDataSource fileDataSource
    ) {
        this.fileDataSource = fileDataSource;
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
                        finalData.postValue(new DataWrapper<>(new CustomException(Utils.createErrorMessage(e.getMessage()))));
                        dispose();
                    }
                });

        return finalData;
    }

}