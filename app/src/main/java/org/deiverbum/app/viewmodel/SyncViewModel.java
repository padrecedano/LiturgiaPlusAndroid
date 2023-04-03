package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.repository.SyncRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SyncViewModel extends ViewModel {

    private final SyncRepository mRepository;

    @Inject
    public SyncViewModel(SyncRepository repository) {
        mRepository = repository;
    }

    public LiveData<SyncStatus> getObservable() {
        return mRepository.getFromDB();
    }
    public LiveData<Integer> getInitialSyncStatus() {
        return mRepository.getInitialSyncStatus();
    }
    public LiveData<Integer> getYearClean() {
        return mRepository.getYearClean();
    }

    public void launchSyncWorker(){
        mRepository.launchSyncWorker();
    }
    public void initialSync(){
        mRepository.initialSync();
    }

    public void cleanUp(int lastYear) {
        mRepository.launchCleanUp(lastYear);
    }
}