package org.deiverbum.app.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

import org.deiverbum.app.repository.TodayRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TodayViewModel extends ViewModel {

    @Inject
    public TodayViewModel() {
    }
}