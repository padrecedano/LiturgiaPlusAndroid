package org.deiverbum.app.data.source.remote.firebase;

import static org.deiverbum.app.utils.Configuration.BIBLIA_PATH;
import static org.deiverbum.app.utils.Configuration.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.DATA_NOTFOUND;
import static org.deiverbum.app.utils.Constants.DOC_NOTFOUND;
import static org.deiverbum.app.utils.Constants.ERR_BIBLIA;
import static org.deiverbum.app.utils.Constants.FIREBASE_SANTOS;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BibliaLibros;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.utils.Utils;

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

    @Inject
    public FirebaseDataSource() {
    }


    /**
     * <p>Obtiene un observable con la Homilía según las parámetros dados.</p>
     *
     * @param date La fecha a buscar
     * @return Objeto con la homilía o error
     */
    public Single<DataWrapper<Homilias, CustomException>> getHomilias(final String date) {
        return Single.create(emitter -> {
            DocumentReference calRef = firebaseFirestore.collection(CALENDAR_PATH).document(date);
            calRef.addSnapshotListener((calSnapshot, e) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    DocumentReference dataRef = calSnapshot.getDocumentReference("homilias");
                    if (e != null || dataRef == null) {
                        emitter.onError(new Exception(DATA_NOTFOUND));
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot data) -> {
                        //data.postValue(data.toObject(Homilias.class));
                        emitter.onSuccess(new DataWrapper<>(data.toObject(Homilias.class)));
                    });
                } else {
                    emitter.onError(new Exception(DATA_NOTFOUND));
                }
            });
        });
    }

    /**
     * <p>Obtiene un observable con un objeto Santo o error.</p>
     *
     * @param month El mes
     * @param day   El día
     * @return Datos del Santo o error
     */
    public Single<DataWrapper<Santo, CustomException>> getSantos(String month, String day) {
        return Single.create(emitter -> {
            DocumentReference dataRef = firebaseFirestore.document(FIREBASE_SANTOS).collection(month).document(day);
            dataRef.get().addOnSuccessListener((DocumentSnapshot data) -> {
                if (data.exists()) {
                    try {
                        Santo santo = data.toObject(Santo.class);
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

    public Single<DataWrapper<Lecturas, CustomException>> getLecturas(String dateString) {
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
                                    Lecturas theHour =
                                            mSnapshot.toObject(Lecturas.class);
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

    public Single<DataWrapper<Lecturas, CustomException>> getLecturass(final String dateString) {
        return Single.create(emitter -> {
            DocumentReference calRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            calRef.addSnapshotListener((calSnapshot, e) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    DocumentReference dataRef = calSnapshot.getDocumentReference("lecturas");
                    MetaLiturgia meta = calSnapshot.get("metaliturgia",
                            MetaLiturgia.class);

                    if (e != null || dataRef == null) {
                        emitter.onError(new Exception(DATA_NOTFOUND));
                        return;
                    }
                    dataRef.get().addOnSuccessListener((DocumentSnapshot data) -> {

                        emitter.onSuccess(new DataWrapper<>(data.toObject(Lecturas.class)));
                    });
                } else emitter.onError(new Exception(DATA_NOTFOUND));
            });
        });
    }


    public Single<DataWrapper<Mixto, CustomException>> getMixtos(final String dateString) {
        return Single.create(emitter -> {
            DocumentReference calRef = firebaseFirestore.collection(CALENDAR_PATH).document(dateString);
            try {
                calRef.addSnapshotListener((calSnapshot, e) -> {
                    if ((calSnapshot != null) && calSnapshot.exists()) {
                        DocumentReference dataRef = calSnapshot.getDocumentReference("lh.0");
                        if (e != null || dataRef == null) {
                            emitter.onError(new Exception(DATA_NOTFOUND));
                            return;
                        }
                        MetaLiturgia meta = calSnapshot.get("metaliturgia", MetaLiturgia.class);

                        dataRef.get().addOnSuccessListener((DocumentSnapshot data) -> {
                            Mixto hora=data.toObject(Mixto.class);
                            if (hora == null) {
                                //hora.setMetaLiturgia(meta);
                                emitter.onError(new RuntimeException());
                                //return;
                            }
                            Objects.requireNonNull(hora).setMetaLiturgia(meta);
                            emitter.onSuccess(new DataWrapper<>(hora));
                        });
                    } else {
                        emitter.onError(new Exception(DATA_NOTFOUND));
                    }
                });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

    }

    public Single<DataWrapper<Mixto, CustomException>> getMixto(String dateString) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference("lh.0");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Mixto theHour =
                                            mSnapshot.toObject(Mixto.class);
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

    public Single<DataWrapper<Oficio, CustomException>> getOficio(String dateString) {
        return Single.create(emitter -> {
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef =
                                document.getDocumentReference("lh.1");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Oficio theHour = mSnapshot.toObject(Oficio.class);
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

    public Single<DataWrapper<Laudes, CustomException>> getLaudes(String dateString) {
        return Single.create(emitter -> {
            DataWrapper<Laudes, CustomException> data = new DataWrapper<>();
            DocumentReference docRef =
                    firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef = document.getDocumentReference("lh.2");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Laudes theHour = mSnapshot.toObject(Laudes.class);
                                    Objects.requireNonNull(theHour).setMetaLiturgia(meta);
                                    data.postValue(theHour);
                                    emitter.onSuccess(data);
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


    public Single<DataWrapper<Intermedia, CustomException>> getIntermedia(String dateString, int hourId) {
        return Single.create(emitter -> {
            DataWrapper<Intermedia, CustomException> data = new DataWrapper<>();
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String mField = String.format("lh.%s", hourId);
                        DocumentReference dataRef = document.getDocumentReference(mField);
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Intermedia theHour = mSnapshot.toObject(Intermedia.class);
                                    Objects.requireNonNull(theHour).setMetaLiturgia(meta);
                                    data.postValue(theHour);
                                    emitter.onSuccess(data);
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

    public Single<DataWrapper<Visperas, CustomException>> getVisperas(String dateString) {
        return Single.create(emitter -> {
            DataWrapper<Visperas, CustomException> data = new DataWrapper<>();
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef = document.getDocumentReference("lh.6");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {

                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Visperas theHour = mSnapshot.toObject(Visperas.class);
                                    Objects.requireNonNull(theHour).setMetaLiturgia(meta);
                                    data.postValue(theHour);
                                    emitter.onSuccess(data);
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


    public Single<DataWrapper<Comentarios, CustomException>> getComentarios(String dateString) {
        return Single.create(emitter -> {
            DataWrapper<Comentarios, CustomException> finalData = new DataWrapper<>();
            DocumentReference docRef = firebaseFirestore.collection(CALENDAR_PATH).document(Utils.toDocument(dateString));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference dataRef = document.getDocumentReference("comentarios");
                        MetaLiturgia meta = document.get("metaliturgia", MetaLiturgia.class);
                        try {
                            Objects.requireNonNull(dataRef).get().addOnSuccessListener((DocumentSnapshot mSnapshot) -> {
                                if (mSnapshot.exists()) {
                                    Comentarios theData = mSnapshot.toObject(Comentarios.class);
                                    Objects.requireNonNull(theData).setMetaLiturgia(meta);
                                    finalData.postValue(theData);
                                    emitter.onSuccess(finalData);
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
     * <p>Obtiene un observable con el libro de la Biblia según los parámetros dados.</p>
     *
     * @param bookId El ID del libro a buscar
     * @return Objeto con el libro o error
     */
    public Single<DataWrapper<BibliaLibros, CustomException>> getBiblia(final int bookId) {
        return Single.create(emitter -> {
            DocumentReference calRef = firebaseFirestore.collection(BIBLIA_PATH).document(String.valueOf(bookId));
            calRef.addSnapshotListener((calSnapshot, e) -> {
                if ((calSnapshot != null) && calSnapshot.exists()) {
                    emitter.onSuccess(new DataWrapper<>(calSnapshot.toObject(BibliaLibros.class)));
                } else {
                    emitter.onError(new Exception(ERR_BIBLIA));
                }
            });
        });
    }
}
