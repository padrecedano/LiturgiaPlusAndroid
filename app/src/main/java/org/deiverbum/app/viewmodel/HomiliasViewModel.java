package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.repository.HomiliasRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomiliasViewModel extends ViewModel {

    private final HomiliasRepository mRepository;


    @Inject
    public HomiliasViewModel(HomiliasRepository repository) {
        mRepository = repository;
    }

    public MediatorLiveData<DataWrapper <Homilias, CustomException>> getObservable(String date) {
        return mRepository.getData(date);
    }

}