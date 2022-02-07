package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.model.OracionSimple;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.ViaCrucis;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio para el módulo Oraciones.</p>
 * @author A. Cedano
 * @since 2022.01.01
 */
public class OracionesRepository {
    private final FileDataSource fileDataSource;


    @Inject
    public OracionesRepository(FileDataSource fileDataSource) {
        this.fileDataSource = fileDataSource;
    }

    public MutableLiveData<DataWrapper<Rosario,CustomException>> getRosario(int param) {
        MutableLiveData<DataWrapper<Rosario, CustomException>> finalData = new MediatorLiveData<>();
        fileDataSource.getRosario(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Rosario,CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<Rosario,CustomException> data) {
                        finalData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finalData.postValue(new DataWrapper<>(new CustomException(e)));
                        dispose();
                    }});

        return finalData;
    }

    public MediatorLiveData<DataWrapper<OracionSimple,CustomException>> getOracionSimple(String rawPath) {
        MediatorLiveData<DataWrapper<OracionSimple, CustomException>> finalData = new MediatorLiveData<>();

        fileDataSource.getOracionSimple(rawPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<OracionSimple,CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<OracionSimple,CustomException> data) {
                        finalData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finalData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s",e.getMessage(),ERR_REPORT))));
                        dispose();
                    }
                });
        return finalData;
    }

    public MutableLiveData<DataWrapper<ViaCrucis,CustomException>> getViaCrucis(String rawPath) {
        MutableLiveData<DataWrapper<ViaCrucis, CustomException>> finalData = new MediatorLiveData<>();
        fileDataSource.getViaCrucis(rawPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<ViaCrucis,CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<ViaCrucis,CustomException> data) {
                        finalData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finalData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s",e.getMessage(),ERR_REPORT))));
                        dispose();
                    }});

        return finalData;
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

