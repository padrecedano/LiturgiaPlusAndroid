package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.repository.FileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FileViewModel extends ViewModel {
    final FileRepository mRepository;

    @Inject
    public FileViewModel(FileRepository repository) {
        mRepository = repository;
    }

    public LiveData<DataWrapper<Book, CustomException>> getBook(String rawPath) {
        return mRepository.getBook(rawPath);
    }


}