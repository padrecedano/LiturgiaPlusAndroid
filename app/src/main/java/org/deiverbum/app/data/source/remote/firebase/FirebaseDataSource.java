package org.deiverbum.app.data.source.remote.firebase;

import static org.deiverbum.app.utils.Configuration.BIBLIA_PATH;
import static org.deiverbum.app.utils.Configuration.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.DATA_NOTFOUND;
import static org.deiverbum.app.utils.Constants.DOC_NOTFOUND;
import static org.deiverbum.app.utils.Constants.ERR_BIBLIA;
import static org.deiverbum.app.utils.Constants.FIREBASE_SANTOS;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SnapshotMetadata;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BibleBooks;
import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReadingList;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.crud.CrudToday;
import org.deiverbum.app.utils.Utils;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


/**
 * <p>Esta clase hace las peticiones a los datos que se encuentran en Firebase.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */

//TODO Listener registration MUY IMPORTANTE
public class FirebaseDataSource {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final TodayDao mTodayDao;

    @Inject
    public FirebaseDataSource(TodayDao mTodayDao
    ) {
        this.mTodayDao = mTodayDao;
    }

    /**
     * <p>Obtiene un observable con un objeto Saint o error.</p>
     *
     * @param month El mes
     * @param day   El día
     * @return Datos del Saint o error
     */
    public Single<DataWrapper<SaintLife, CustomException>> getSantos(String month, String day) {
        return Single.create(emitter -> {
            DocumentReference dataRef = firebaseFirestore.document(FIREBASE_SANTOS).collection(month).document(day);
            dataRef.get().addOnSuccessListener((DocumentSnapshot data) -> {
                if (data.exists()) {
                    try {
                        SaintLife santo = data.toObject(SaintLife.class);
                        if (santo != null) {
                            santo.setMes(month);
                            santo.setDia(day);
                        }
                        emitter.onSuccess(new DataWrapper<>(santo));
                    } catch (Exception e) {
                        emitter.onError(new Exception(e));
                    }
                } else {
                    emitter.onError(new CustomException(Utils.createErrorMessage(DATA_NOTFOUND, month + day)));
                }
            });
        });
    }

    public Single<DataWrapper<MassReadingList, CustomException>> getLecturas(String dateString) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference("lecturas");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    MassReadingList theHour =
                                            mSnapshot.toObject(MassReadingList.class);
                                    Objects.requireNonNull(theHour).setMetaLiturgia(meta);
                                    emitter.onSuccess(new DataWrapper<>(theHour));
                                } else {
                                    emitter.onError(new Exception(DATA_NOTFOUND));
                                }
                            });
                        } catch (Exception e) {
                            emitter.onError(new Exception(e));
                        }
                    } else {
                        emitter.onError(new Exception(DOC_NOTFOUND));

                    }
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    public Single<DataWrapper<BibleCommentList, CustomException>> getComentarios(String dateString) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference("comentarios");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    BibleCommentList theData =
                                            mSnapshot.toObject(BibleCommentList.class);
                                    Objects.requireNonNull(theData).setMetaLiturgia(meta);
                                    emitter.onSuccess(new DataWrapper<>(theData));
                                } else {
                                    emitter.onError(new Exception(DATA_NOTFOUND));
                                }
                            });
                        } catch (Exception e) {
                            emitter.onError(new Exception(e));
                        }
                    } else {
                        emitter.onError(new Exception(DOC_NOTFOUND));

                    }
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    /**
     * <p>Obtiene un observable con la Homilía según las parámetros dados.</p>
     *
     * @param dateString La fecha a buscar
     * @return Objeto con la homilía o error
     */

    public Single<DataWrapper<Homily, CustomException>> getHomilias(String dateString) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference("homilias");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Homily theData =
                                            mSnapshot.toObject(Homily.class);
                                    Objects.requireNonNull(theData).setMetaLiturgia(meta);
                                    emitter.onSuccess(new DataWrapper<>(theData));
                                } else {
                                    emitter.onError(new Exception(DATA_NOTFOUND));
                                }
                            });
                        } catch (Exception e) {
                            emitter.onError(new Exception(e));
                        }
                    } else {
                        emitter.onError(new Exception(DOC_NOTFOUND));

                    }
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }


    public Single<DataWrapper<MetaLiturgia, CustomException>> getMetaLiturgia(String dateString) {
        return Single.create(emitter -> {
            DataWrapper<MetaLiturgia, CustomException> data = new DataWrapper<>();
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        data.postValue(meta);
                        emitter.onSuccess(data);
                    } else {
                        emitter.onError(new Exception(DOC_NOTFOUND));
                    }
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    /**
     * <p>Obtiene un observable con el libro de la Biblia según los parámetros dados.</p>
     *
     * @param bookId El ID del libro a buscar
     * @return Objeto con el libro o error
     */
    public Single<DataWrapper<BibleBooks, CustomException>> getBiblia(final int bookId) {
        return Single.create(emitter -> {
            DocumentReference calRef = firebaseFirestore.collection(BIBLIA_PATH).document(String.valueOf(bookId));
            calRef.addSnapshotListener((calSnapshot, e) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    emitter.onSuccess(new DataWrapper<>(calSnapshot.toObject(BibleBooks.class)));
                } else {
                    emitter.onError(new Exception(ERR_BIBLIA));
                }
            });
        });
    }

    public Single<DataWrapper<Liturgy, CustomException>> getBreviary(String dateString, int hourId) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference(String.format(new Locale("es"), "lh.%d", hourId));
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        Objects.requireNonNull(meta).setIdHour(hourId);

                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Liturgy theHour = mSnapshot.toObject(Liturgy.class);
                                    Objects.requireNonNull(theHour).setMetaLiturgia(meta);
                                    emitter.onSuccess(new DataWrapper<>(theHour));
                                } else {
                                    emitter.onError(new Exception(DATA_NOTFOUND));
                                }
                            });
                        } catch (Exception e) {
                            emitter.onError(new Exception(e));
                        }
                    } else {
                        emitter.onError(new Exception(DOC_NOTFOUND));

                    }
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }



    public void getSync() {
        firebaseFirestore.collection("/2022_02/sync/today")
                .whereGreaterThanOrEqualTo("todayDate", Utils.getTodayMinus(3))
                .addSnapshotListener(
                        (snapshots, e) -> {
                            if (e != null) {
                                System.err.println("Listen failed: " + e);
                                return;
                            }
                            CrudToday crudToday = new CrudToday();
                            for (DocumentChange dc : Objects.requireNonNull(snapshots).getDocumentChanges()) {
                                SnapshotMetadata metaData = dc.getDocument().getMetadata();
                                DocumentChange.Type dcType = dc.getType();
                                //DocumentSnapshot doc=dc.getDocument();
                                if (dcType == DocumentChange.Type.ADDED && !metaData.isFromCache()) {
                                    crudToday.addCreate(dc.getDocument().toObject(Today.class));
                                }
                                if (dcType == DocumentChange.Type.MODIFIED && !metaData.isFromCache()) {
                                    crudToday.addUpdate(dc.getDocument().toObject(Today.class));
                                }
                                if (dcType == DocumentChange.Type.REMOVED && !metaData.isFromCache()) {
                                    crudToday.addDelete(dc.getDocument().toObject(Today.class));
                                }
                            }
                            try {
                                if (crudToday.c != null && !crudToday.c.isEmpty()) {
                                    mTodayDao.todayInsertAll(crudToday.c);
                                }
                                if (crudToday.u != null && !crudToday.u.isEmpty()) {
                                    mTodayDao.todayUpdateAll(crudToday.u);
                                }
                                if (crudToday.d != null && !crudToday.d.isEmpty()) {
                                    mTodayDao.todayDeleteAll(crudToday.d);
                                }
                            } catch (Exception ex) {
                                Log.e("ERR", ex.getMessage());
                            }

                        });
    }

}
