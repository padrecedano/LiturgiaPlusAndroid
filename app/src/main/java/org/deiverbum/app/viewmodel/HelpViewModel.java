package org.deiverbum.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("<p>Consulta el siguiente enlace con algunas instrucciones sobre el funcionamiento de la App:</p><a href=\"https://www.liturgiaplus.app/ayuda\">Ayuda en línea</a>");
    }

    public LiveData<String> getText() {
        return mText;
    }
}