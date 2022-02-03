package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.repository.ComentariosRepository;
import org.deiverbum.app.repository.LecturasRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ComentariosViewModel extends ViewModel {

    private ComentariosRepository mRepository;


    @Inject
    public ComentariosViewModel(ComentariosRepository repository) {
        mRepository = repository;
    }

    public MediatorLiveData getObservable(String date) {
        return mRepository.getData(date);
    }

}