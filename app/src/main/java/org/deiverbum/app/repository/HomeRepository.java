package org.deiverbum.app.repository;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;

import javax.inject.Inject;

/**
 * <p>Repositorio de datos para el módulo Home.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

public class HomeRepository {
    private final FirebaseDataSource firebaseDataSource;

    @Inject
    public HomeRepository(FirebaseDataSource firebaseDataSource
    ) {
        this.firebaseDataSource = firebaseDataSource;

    }

    /**
     * Agrega un evento de escucha para sincronizar las fechas del calendario.
     * Los datos serán recibidos desde Firebase mediante {@link FirebaseDataSource#getSync()}
     */

    public void getFromFirebase() {
        firebaseDataSource.getSync();
    }



}