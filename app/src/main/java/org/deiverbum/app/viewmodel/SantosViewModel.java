package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;
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

    public LiveData<DataWrapper<Saint, CustomException>> getObservable(String month,
                                                                       String day) {
        return null;//mRepository.getData(month, day);
    }

    public LiveData<DataWrapper<SaintLife, CustomException>> getSaintLife(String theDate) {
        return mRepository.getSaintDB(theDate);
    }

}