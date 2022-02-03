package org.deiverbum.app.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.repository.AuthorRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class AuthorViewModel extends ViewModel {
    AuthorRepository mRepository;
    //private Context mContext;

    @Inject
    public AuthorViewModel(AuthorRepository repository) {
        mRepository = repository;
        //mContext=context;
    }

    /*public LiveData<String> getText() {
        return mRepository.getText(mContext);
    }*/

    public LiveData<String> getTextR() {
        return mRepository.getTextR();
    }


}