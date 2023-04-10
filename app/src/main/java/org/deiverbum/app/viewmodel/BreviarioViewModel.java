package org.deiverbum.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.repository.BreviarioRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BreviarioViewModel extends ViewModel {
    final BreviarioRepository mRepository;

    @Inject
    public BreviarioViewModel(BreviarioRepository repository) {
        mRepository = repository;
    }

    public MutableLiveData<DataWrapper<Today, CustomException>> getToday(String dateString, int hourId) {
        return mRepository.getFromLocal(dateString, hourId);
    }
}