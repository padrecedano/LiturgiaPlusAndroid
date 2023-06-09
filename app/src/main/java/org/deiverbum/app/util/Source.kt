package org.deiverbum.app.util
/**
 * Enumera las diferentes fuentes de datos que se usan en la aplicación.
 */

enum class Source {
    /**
     * Base de datos local o archivos locales.
     */
    LOCAL,
    /**
        * Servidor remoto, accesible mediante la API.
     */
    NETWORK,

    /**
     * Base de datos en Firebase.
     */
    FIREBASE,

    /**
     * Fuente de datos para eventuales pruebas.
     */
    MOCK
}