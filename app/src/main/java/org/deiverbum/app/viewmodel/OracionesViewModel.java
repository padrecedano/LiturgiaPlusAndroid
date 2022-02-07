package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.OracionSimple;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.ViaCrucis;
import org.deiverbum.app.repository.OracionesRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OracionesViewModel extends ViewModel {

    private final OracionesRepository mRepository;

    @Inject
    public OracionesViewModel(OracionesRepository repository) {
        mRepository = repository;
    }


    public LiveData<DataWrapper<Rosario, CustomException>> getRosario(int param) {
        return mRepository.getRosario(param);
    }

    public LiveData<DataWrapper<OracionSimple, CustomException>> getOracionSimple(String rawPath) {
        return mRepository.getOracionSimple(rawPath);
    }

    public LiveData<DataWrapper<ViaCrucis, CustomException>> getViaCrucis(String rawPath) {
        return mRepository.getViaCrucis(rawPath);
    }

}