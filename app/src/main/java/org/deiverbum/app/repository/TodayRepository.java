package org.deiverbum.app.repository;

import org.deiverbum.app.data.source.remote.network.ApiService;

import javax.inject.Inject;

/**
 * <p>Repositorio de datos para el módulo Homilías.</p>
 * <p>Orden de búsqueda: </p>
 * <ul>
 *     <li>Firebase</li>
 *     <li>Api</li>
 * </ul>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class TodayRepository {
    final ApiService apiService;

    @Inject
    public TodayRepository(ApiService apiService) {
        this.apiService = apiService;
    }

}

