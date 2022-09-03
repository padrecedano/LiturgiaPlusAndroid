package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.repository.HomiliasRepository;
import org.deiverbum.app.repository.TodayRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TodayViewModel extends ViewModel {

    private final TodayRepository mRepository;
    private WorkManager mWorkManager;

    @Inject
    public TodayViewModel(TodayRepository repository) {
        mRepository = repository;
    }
}