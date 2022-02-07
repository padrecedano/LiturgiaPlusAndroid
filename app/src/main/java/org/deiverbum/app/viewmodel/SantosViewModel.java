package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.repository.SantosRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SantosViewModel extends ViewModel {
    SantosRepository mRepository;

    @Inject
    public SantosViewModel(SantosRepository repository) {
        mRepository = repository;
    }

    public LiveData<DataWrapper<Santo, CustomException>> getObservable(String month,
                                                                       String day) {
        return mRepository.getData(month, day);
    }


}