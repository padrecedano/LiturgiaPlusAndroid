package org.deiverbum.app.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;

import org.deiverbum.app.model.Comentario;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.repository.AboutRepository;
import org.deiverbum.app.utils.Event;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class CalendarioViewModel extends ViewModel {
    AboutRepository mRepository;
    private Context mContext;

    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();
    public void selectItem(String item) {
        selectedItem.setValue(item);
    }
    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }

    private MutableLiveData<Comentario> todoMutableLiveData = new MutableLiveData<>();
    public LiveData<Comentario> todo = todoMutableLiveData;
    private MutableLiveData<Event<String>> _errorLiveData = new MutableLiveData<>();
    public LiveData<Event<String>> errorLiveData = _errorLiveData;


    private MutableLiveData<Boolean> _progressLiveData = new MutableLiveData<>(false);
    public LiveData<Boolean> progressLiveData = _progressLiveData;


    //----Firebase
    private FirebaseDataSource firebaseDataSource = new FirebaseDataSource();
    private MutableLiveData<Today> todayData = new MutableLiveData<>();

    public LiveData<Today> todayObserver() {
        return todayData;
    }
    /*public void getToday(String date) {
        firebaseDataSource.getTodayData(date);
    }*/

    //---

    @Inject
    public CalendarioViewModel(
            @ApplicationContext Context context,
            AboutRepository repository) {

    mRepository =repository;
    mContext=context;
        //firebaseDataSource.getQuizCategoryData();

    }



    public LiveData<String> getText() {
        return mRepository.getText(mContext);
    }

    public void getTodo(String id) {
        _progressLiveData.postValue(true);
    }


}