package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.repository.CompletasRepository;
import org.deiverbum.app.repository.LecturasRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CompletasViewModel extends ViewModel {

    private CompletasRepository mRepository;


    @Inject
    public CompletasViewModel(CompletasRepository repository) {
        mRepository = repository;
    }

    public void getMeta(String date) {
         mRepository.getData(date);
    }

    public MediatorLiveData getObservable() {
        return mRepository.getLiveData();
    }

}