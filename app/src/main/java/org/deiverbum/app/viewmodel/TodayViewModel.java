package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.UserWithPlaylistsAndSongs;
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
        //mWorkManager = WorkManager.getInstance(mRepository.getApplication());
    }

    public MediatorLiveData<DataWrapper <Homilias, CustomException>> getObservable(String date) {
        return mRepository.getData(date);
    }

    public LiveData<Today> getToday(String date) {
        return mRepository.getFavoritePokemon(date);
    }
/*
    public LiveData<Map<Integer, List<Integer>>> getSalmodiaB(String date) {
        return mRepository.getSalmodiaB("");
    }
    */
    public LiveData<List<SalmodiaWithSalmos>> getSalmodia(String date) {
        return mRepository.getSalmos("");
    }

    public LiveData<SalmoEntity> getSalmo() {
        return mRepository.getSalmo();
    }
/*
    public LiveData<Map<Salmodia, List<Salmo>>> loadUserAndBookNames() {
        return mRepository.loadUserAndBookNames();
    }
*/
    public LiveData<UserWithPlaylistsAndSongs> getUsersWithPlaylistsAndSongs(String date) {
        return mRepository.getUsersWithPlaylistsAndSongs("");
    }

    public LiveData<Oficio> getTodayWithOficio(String theDate) {
        return mRepository.transformedOficio("20220325");
    }

    public LiveData<Laudes> getLaudes(String theDate) {
        return mRepository.mappedLaudes("20220325");
    }

    public LiveData<Intermedia> getTercia(String theDate) {
        return mRepository.getTercia("20220325");
    }

    public LiveData<Mixto> getMixto(String theDate) {
        return mRepository.getMixto("20220325");
    }

    public LiveData<Oficio> getTodayWithOficioB(String theDate) {
        return mRepository.transformedOficioB("20220325");
    }
/*
    public LiveData<List<SalmodiaWithSalmos>> getSalmodiaWithSalmos(String date) {
        return mRepository.g("");
    }*/
    public LiveData<Integer> getLastLive(String date) {
        return mRepository.getLastLive();
    }


    public void fetchData(String theDate) {
        mRepository.fetchData(theDate);
    }
/*
    public LiveData<Salmodia> getS(String mDate) {
        return mRepository.getS("");
    }*/
}