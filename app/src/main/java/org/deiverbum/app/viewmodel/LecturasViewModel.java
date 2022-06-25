package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.repository.LecturasRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LecturasViewModel extends ViewModel {

    private final LecturasRepository mRepository;


    @Inject
    public LecturasViewModel(LecturasRepository repository) {
        mRepository = repository;
    }

    public LiveData<DataWrapper<Lecturas, CustomException>> getObservable(String date) {
        return mRepository.getFromDB(date);
    }

}