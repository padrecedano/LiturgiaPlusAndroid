package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.repository.ComentariosRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ComentariosViewModel extends ViewModel {

    private final ComentariosRepository mRepository;


    @Inject
    public ComentariosViewModel(ComentariosRepository repository) {
        mRepository = repository;
    }

    public MutableLiveData<DataWrapper<Comentarios, CustomException>> getObservable(String date) {
        return mRepository.getComentarios(date);
    }

}