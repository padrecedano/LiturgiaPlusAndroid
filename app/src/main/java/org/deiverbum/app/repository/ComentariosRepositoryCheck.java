package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.utils.FirestoreLiveData;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class ComentariosRepositoryCheck {
    MutableLiveData<List<Breviario>> blogListMutableLiveData;
    FirebaseFirestore mFirestore;
    MutableLiveData<Breviario> blogMutableLiveData;
    private String strFechaHoy=Utils.getHoy();
    private LiveData<Liturgia> mLiturgia;

    @Inject
    public ComentariosRepositoryCheck() {
        this.blogListMutableLiveData = new MutableLiveData<>();
        //define firestore
        mFirestore = FirebaseFirestore.getInstance();
        //define bloglist
        blogMutableLiveData = new MutableLiveData<>();

    }
    public LiveData<Liturgia> productListening(HashMap<String,String> mParams) {
        return new FirestoreLiveData<>(mParams, Liturgia.class);
    }

    public LiveData<Liturgia> breviarioListening(HashMap<String,String> mParams) {
        return new FirestoreLiveData<>(mParams, Breviario.class);
    }
    //get blog from firebase firestore
    public MutableLiveData<List<Breviario>> getBlogListMutableLiveData() {
        Log.d("TAG", "getBlogListMutableLiveData: ");
        mFirestore.collection("Blog").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Breviario> blogList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        blogList.add(doc.toObject(Breviario.class));
                    }
                }
                blogListMutableLiveData.postValue(blogList);
            }
        });
        return blogListMutableLiveData;
    }

    public void launchFirestore(int hourID) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fechaDD = strFechaHoy.substring(6, 8);
        String fechaMM = strFechaHoy.substring(4, 6);
        String fechaYY = strFechaHoy.substring(0, 4);
        Liturgia liturgia = new Liturgia();
        DocumentReference calRef = db.collection(CALENDAR_PATH).document(fechaYY).collection(fechaMM).document(fechaDD);
        calRef.addSnapshotListener((calSnapshot, e) -> {
            if ((calSnapshot != null) && calSnapshot.exists()) {
                //mLiturgia = calSnapshot.toObject(Liturgia.class);
                String mField="lh."+hourID;
                Log.d("TAG","rrr");
                DocumentReference dataRef = calSnapshot.getDocumentReference(mField);
                if (e != null || dataRef == null) {
                    //launchVolley();
                    return;
                }
                dataRef.get().addOnSuccessListener((DocumentSnapshot dataSnapshot) -> {
                    liturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                    //mLiturgia.setValue(liturgia);
                    //showData();
                });
            } else {
                //launchVolley();
            }
        });
        //LiveData<Liturgia> l= new LiveData();
        //return null;
    }



}

