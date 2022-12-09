package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.repository.SantosRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@SuppressWarnings("SameReturnValue")
@HiltViewModel
public class SantosViewModel extends ViewModel {
    final SantosRepository mRepository;

    @Inject
    public SantosViewModel(SantosRepository repository) {
        mRepository = repository;
    }


    public LiveData<DataWrapper<SaintLife, CustomException>> getSaintLife(String theDate) {
        return mRepository.getSaintDB(theDate);
    }

}