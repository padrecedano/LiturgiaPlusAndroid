package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.repository.BreviarioRepository;

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
        return mRepository.getMixtoDB(dateString);
    }


    public MutableLiveData<DataWrapper<Oficio, CustomException>> getOficio(String dateString) {
        return mRepository.getOficioDB(dateString);
    }


    public MediatorLiveData<DataWrapper<Laudes, CustomException>> getLaudes(String dateString) {
        return mRepository.getLaudesDB(dateString);
    }

    public MediatorLiveData<DataWrapper<Intermedia, CustomException>> getIntermedia(String dateString, int hourId, String endPoint) {
        return mRepository.getIntermediaDB(dateString, hourId, endPoint);
    }

    public MediatorLiveData<DataWrapper<Visperas, CustomException>> getVisperas(String dateString) {
        return mRepository.getVisperasDB(dateString);
    }

}