package org.deiverbum.app.utils;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.data.wrappers.Resource;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.BreviarioHora;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.IntermediaHora;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaBreviario;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Visperas;

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


public class FirestoreBreviario<T> extends LiveData<T> {

    public static final String TAG = "FirestoreLiveData";
    private ListenerRegistration registration;
    private DocumentReference docRef;
    private Class clazz;
    private Liturgia mLiturgia;
    private Breviario mBreviario;
    private MetaBreviario metaBreviario;
    private MetaLiturgia metaLiturgia;
    private BreviarioHora breviarioHora;
    private DataWrapper<BreviarioHora, CustomException> custom;
    private Resource<BreviarioHora> customB;

    private Resource<T> r;
    private Intermedia hi;
    private IntermediaHora ih;
    final MutableLiveData<Resource<BreviarioHora>> myBeanClass = new MutableLiveData<>();
    private EventListener<DocumentSnapshot> mListener;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Resource<BreviarioHora>> theData; //= new MutableLiveData<>();
    public FirestoreBreviario(HashMap<String, String> mParams) {
        this.clazz = clazz;
        this.docRef = db.document(CALENDAR_PATH + mParams.get("calendar"));
        //this.docRef = db.document("/es/2020_1/liturgia/lh/3/0");

        getLiturgia(mParams.get("hourId"));
    }

    public FirestoreBreviario(HashMap<String, String> mParams, String s) {
        this.clazz = clazz;
        this.docRef = db.document(CALENDAR_PATH + mParams.get("calendar"));
        //this.docRef = db.document("/es/2020_1/liturgia/lh/3/0");
this.custom= new DataWrapper<>();
        getCustom(mParams.get("hourId"));
    }

    public FirestoreBreviario(HashMap<String, String> mParams, String s, String b) {
        this.clazz = clazz;
        this.docRef = db.document(CALENDAR_PATH + mParams.get("calendar"));
        //this.docRef = db.document("/es/2020_1/liturgia/lh/3/0");
        //this.custom= new Resource<T>();
        this.r=new Resource<>(null,null,null);
        this.theData= new MutableLiveData<Resource<BreviarioHora>>();

        getCustomB(mParams.get("hourId"));
    }

    public void getCustomB(String hourId) {
        mListener = (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.i(TAG, "Listen failed.", e);
                setValue((T) e);
                return;
            }
            docRef.addSnapshotListener((calSnapshot, e1) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    metaLiturgia = calSnapshot.get("meta",MetaLiturgia.class);
                    String mField = String.format("lh.%s", hourId);
                    //mField="/es/2020_1/liturgia/lh/3/0";
                    DocumentReference dataRef = calSnapshot.getDocumentReference(mField);
                    if (e != null || dataRef == null) {
                        setValue((T) e);
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                        switch (hourId) {
                            case "0":
                                Mixto mixto = mSnapshot.toObject(Mixto.class);
                                mixto.setMetaLiturgia(metaLiturgia);
                                setValue((T) mixto);
                                break;

                            case "1":
                                Oficio oficio = mSnapshot.toObject(Oficio.class);
                                oficio.setMetaLiturgia(metaLiturgia);
                                setValue((T) oficio);
                                break;

                            case "2":
                                Laudes laudes = mSnapshot.toObject(Laudes.class);
                                laudes.setMetaLiturgia(metaLiturgia);
                                setValue((T) laudes);
                                break;

                            case "3":
                            case "4":
                            case "5":
                                Intermedia hi = mSnapshot.toObject(Intermedia.class);
                                hi.setMetaLiturgia(metaLiturgia);
                                setValue((T) hi);
                                break;

                            case "6":
                                Visperas visperas = mSnapshot.toObject(Visperas.class);
                                visperas.setMetaLiturgia(metaLiturgia);
                                setValue((T) visperas);
                                break;
                        }
                        Log.d("__get", mSnapshot.toString());
                    });
                } else {
                    //custom.postValue(new CustomException("no data"));
                    Log.d("__get__", "noTdataSnapshot.toString()");
                    //myBeanClass=new Resource.error("not data",null);
                    //r.status= Resource.Status.ERROR;
                    //postValue(Resource.error("not data",null));
                    //setValue((T) myBeanClass);
                    //postValue((T) myBeanClass);
                    setValue((T) theData);

                }
            });
        };
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

    public void getCustom(String hourId) {
        mListener = (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.i(TAG, "Listen failed.", e);

                setValue((T) e);

                return;
            }
            docRef.addSnapshotListener((calSnapshot, e1) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    metaLiturgia = calSnapshot.get("meta",MetaLiturgia.class);
                    String mField = String.format("lh.%s", hourId);
                    //mField="/es/2020_1/liturgia/lh/3/0";
                    DocumentReference dataRef = calSnapshot.getDocumentReference(mField);
                    if (e != null || dataRef == null) {
                        setValue((T) e);
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                        switch (hourId) {
                            case "0":
                                Mixto mixto = mSnapshot.toObject(Mixto.class);
                                mixto.setMetaLiturgia(metaLiturgia);
                                setValue((T) mixto);
                                break;

                            case "1":
                                Oficio oficio = mSnapshot.toObject(Oficio.class);
                                oficio.setMetaLiturgia(metaLiturgia);
                                setValue((T) oficio);
                                break;

                            case "2":
                                Laudes laudes = mSnapshot.toObject(Laudes.class);
                                laudes.setMetaLiturgia(metaLiturgia);
                                setValue((T) laudes);
                                break;

                            case "3":
                            case "4":
                            case "5":
                                Intermedia hi = mSnapshot.toObject(Intermedia.class);
                                hi.setMetaLiturgia(metaLiturgia);
                                custom.postValue(hi);
                                setValue((T) custom);
                                break;

                            case "6":
                                Visperas visperas = mSnapshot.toObject(Visperas.class);
                                visperas.setMetaLiturgia(metaLiturgia);
                                custom.postValue(visperas);
                                setValue((T) custom);
                                break;
                        }






                        //Log.d(TAG, String.valueOf((T) mLiturgia.getBreviario().getMetaLiturgia().getFecha()));

                        Log.d("__get", mSnapshot.toString());
                    });
                } else {
                    //custom.status
                    custom.postValue(new CustomException("no data"));
                    Log.d("__get__", "noTdataSnapshot.toString()");
                    setValue((T) custom);
                }
            });
        };
    }


    public void getLiturgia(String hourId) {

        mListener = (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.i(TAG, "Listen failed.", e);
                return;
            }
            docRef.addSnapshotListener((calSnapshot, e1) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    //mLiturgia = calSnapshot.toObject(Liturgia.class);
                    //metaBreviario = calSnapshot.toObject(MetaBreviario.class);
                    metaLiturgia = calSnapshot.get("meta",MetaLiturgia.class);



                    //Log.d(TAG,mLiturgia.getMetaLiturgia().getFecha());
                    String mField = String.format("lh.%s", hourId);
                    //mField="/es/2020_1/liturgia/lh/3/0";
                    DocumentReference dataRef = calSnapshot.getDocumentReference(mField);
                    if (e != null || dataRef == null) {
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                        //mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                        //setValue((T) mLiturgia);
                        //mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                        //setValue((T) mLiturgia);
                        //hi=dataSnapshot.toObject(Intermedia.class);
                        switch (hourId) {
                            case "0":
                                Mixto mixto = mSnapshot.toObject(Mixto.class);
                                //mixto.setMetaBreviario(metaBreviario);
                                mixto.setMetaLiturgia(metaLiturgia);

                                setValue((T) mixto);
                                break;

                            case "1":
                                Oficio oficio = mSnapshot.toObject(Oficio.class);
                                oficio.setMetaLiturgia(metaLiturgia);
                                setValue((T) oficio);
                                break;

                            case "2":
                                Laudes laudes = mSnapshot.toObject(Laudes.class);
                                laudes.setMetaLiturgia(metaLiturgia);
                                setValue((T) laudes);
                                break;

                            case "3":
                            case "4":
                            case "5":
                                Intermedia hi = mSnapshot.toObject(Intermedia.class);
                                hi.setMetaLiturgia(metaLiturgia);
                                setValue((T) hi);
                                break;

                            case "6":
                                Visperas visperas = mSnapshot.toObject(Visperas.class);
                                visperas.setMetaLiturgia(metaLiturgia);
                                setValue((T) visperas);
                                break;
                        }






                        //Log.d(TAG, String.valueOf((T) mLiturgia.getBreviario().getMetaLiturgia().getFecha()));

                        Log.d("__get", mSnapshot.toString());
                    });
                } else {
                    Log.d("__get__", "noTdataSnapshot.toString()");
                }
            });
        };
    }
}
