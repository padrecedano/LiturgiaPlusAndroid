package org.deiverbum.app.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BibliaLibros;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio para el módulo Biblia.</p>
 * @author A. Cedano
 * @since 2022.01.01
 */
public class BibliaRepository {
    private final FirebaseDataSource firebaseDataSource;

    @Inject
    public BibliaRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    public MutableLiveData<DataWrapper<BibliaLibros,CustomException>> getLibro(int param) {
        MutableLiveData<DataWrapper<BibliaLibros, CustomException>> finalData = new MediatorLiveData<>();
        firebaseDataSource.getBiblia(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<BibliaLibros,CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<BibliaLibros,CustomException> data) {
                        finalData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finalData.postValue(new DataWrapper<>(new CustomException(e.getMessage())));
                        dispose();
                    }});

        return finalData;
    }


}

