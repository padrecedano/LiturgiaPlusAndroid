package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BibleBooks;
import org.deiverbum.app.repository.BibliaRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel para el módulo Biblia
 * @author A. Cedano
 * @since 2022.01.01
 */
@HiltViewModel
public class BibliaViewModel extends ViewModel {
    private final BibliaRepository mRepository;

@Inject
    public BibliaViewModel(BibliaRepository repository) {
        mRepository = repository;
    }

    public LiveData<DataWrapper<BibleBooks, CustomException>> getLibro(int param) {
        return mRepository.getLibro(param);
    }
}