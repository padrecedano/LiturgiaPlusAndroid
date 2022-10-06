package org.deiverbum.app.viewmodel;

import androidx.lifecycle.ViewModel;

import org.deiverbum.app.repository.HomeRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final HomeRepository mRepository;

    @Inject
    public HomeViewModel(HomeRepository repository) {
        mRepository = repository;
    }

    public void callFirestore() {
         mRepository.getFromFirebase();
    }

}