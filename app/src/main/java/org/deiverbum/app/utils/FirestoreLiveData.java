package org.deiverbum.app.utils;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.BreviarioHora;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.IntermediaHora;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaBreviario;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Mixto;

import java.util.HashMap;


/**
 * <p>
 *     Clase ayudadora para peticiones a Firestore.
 *     Es invocada desde el repositorio, seteando al {@link LiveData}
 *     una instancia de la clase pasada en <code>T</code>.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @date 18/11/21
 * @since 2021.1
 *
 * @param <T> Genérico para el tipo de clase que recibirá el LiveData
 */


public class FirestoreLiveData<T> extends LiveData<T> {

    public static final String TAG = "FirestoreLiveData";
    private ListenerRegistration registration;
    private DocumentReference docRef;
    private Class clazz;
    private Liturgia mLiturgia;
    private Breviario mBreviario;
    private MetaBreviario metaBreviario;
    private MetaLiturgia metaLiturgia;
    private BreviarioHora breviarioHora;

    private Intermedia hi;
    private IntermediaHora ih;

    private EventListener<DocumentSnapshot> mListener;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirestoreLiveData(HashMap<String, String> mParams, Class clazz) {
        this.clazz = clazz;
        this.docRef = db.document(CALENDAR_PATH + mParams.get("calendar"));
        //this.docRef = db.document("/es/2020_1/liturgia/lh/3/0");

        getLiturgia(mParams.get("hourID"));
    }

    @Override
    protected void onActive() {
        super.onActive();
        registration = docRef.addSnapshotListener(mListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!hasActiveObservers()) {
            registration.remove();
            registration = null;
        }
    }

    public void getLiturgia(String hourID) {
        mListener = (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.i(TAG, "Listen failed.", e);
                return;
            }
            docRef.addSnapshotListener((calSnapshot, e1) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    //mLiturgia = calSnapshot.toObject(Liturgia.class);
                    //metaBreviario = calSnapshot.toObject(MetaBreviario.class);
                    IntermediaHora h= calSnapshot.toObject(IntermediaHora.class);


                    //Log.d(TAG,mLiturgia.getMetaLiturgia().getFecha());
                    String mField = String.format("lh.%s", hourID);
                    //mField="/es/2020_1/liturgia/lh/3/0";
                    DocumentReference dataRef = calSnapshot.getDocumentReference(mField);
                    if (e != null || dataRef == null) {
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot dataSnapshot) -> {
                        //mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                        //setValue((T) mLiturgia);
                        //mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                        //setValue((T) mLiturgia);
                        //hi=dataSnapshot.toObject(Intermedia.class);
                        Mixto ih=dataSnapshot.toObject(Mixto.class);


                        //ih.setMetaBreviario(metaBreviario);

                        setValue((T) ih);

                        //Log.d(TAG, String.valueOf((T) mLiturgia.getBreviario().getMetaLiturgia().getFecha()));

                        Log.d("__get", dataSnapshot.toString());
                    });
                } else {
                    Log.d("__get__", "noTdataSnapshot.toString()");
                }
            });
        };
    }
}
