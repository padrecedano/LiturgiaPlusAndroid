package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.repository.BreviarioRepository;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BreviarioViewModel extends ViewModel {
    BreviarioRepository mRepository;

    @Inject
    public BreviarioViewModel(BreviarioRepository repository) {
        mRepository = repository;
    }


    public MutableLiveData<DataWrapper<Mixto, CustomException>> getMixto(String dateString) {
        return mRepository.getMixto(Utils.toDocument(dateString));
    }

    public MediatorLiveData test() {
        return null;//mRepository.test();
    }

    public MutableLiveData<DataWrapper<Oficio, CustomException>> getOficio(String dateString) {
        return mRepository.getOficio(dateString);
    }

    public MediatorLiveData<DataWrapper<Laudes, CustomException>> getLaudes(String dateString) {
        return mRepository.getLaudes(dateString);
    }


    public MediatorLiveData getIntermedia(String dateString, int hourId, String endPoint) {
        return mRepository.getIntermedia(dateString, hourId, endPoint);
    }



    public MediatorLiveData getVisperas(String dateString) {
        return mRepository.getVisperas(dateString);
    }

/*
    public LiveData<DataWrapper<Oficio, CustomException>> getOficio(String dateString) {
        return mRepository.getOficio(dateString);

    }

 */
}