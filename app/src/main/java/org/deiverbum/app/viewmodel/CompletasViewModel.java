package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.repository.CompletasRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompletasViewModel extends ViewModel {

    private final CompletasRepository mRepository;


    @Inject
    public CompletasViewModel(CompletasRepository repository) {
        mRepository = repository;
    }

    public void getMeta(String date) {
        mRepository.getData(date);
    }

    public MediatorLiveData<DataWrapper<Completas, CustomException>> getObservable() {
        return mRepository.getLiveData();
    }

}