package org.deiverbum.app.util

import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.EUCHARISTIA_BREVIS_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_ORDINARIUM_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_ORDINARIUM_SACERDOS
import org.deiverbum.app.util.Constants.EUCHARISTIA_VERBUM_BREVIS
import org.deiverbum.app.util.Constants.EUCHARISTIA_VERBUM_EXTENSA
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_ALTER
import org.deiverbum.app.util.Constants.EUCHARISTIA_VIATICUM_SACERDOS
import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_ANGELUS
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_COMMENDATIONE_MORIENTIUM
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_LITANIES
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_REGINA
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_ARTICULO_MORTIS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_IN_DUBIO
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_SINE_VIATICUM
import org.deiverbum.app.util.Constants.UNCTIONIS_ORDINARIUM

class FileNameUtil {
    companion object {
        val fileMap = hashMapOf(
            "Sobre Liturgia+" to FILE_ABOUT,
            "Autor" to FILE_AUTHOR,
            "Ayuda" to FILE_HELP,
            "Agradecimientos" to FILE_THANKS,
            "Novedades" to FILE_NEW,
            "Política de Privacidad" to FILE_PRIVACY,
            "Términos y Condiciones" to FILE_TERMS,
            "Bautismo: Peligro de muerte" to FILE_BAPTISMUS,
            "Bautismo CIC" to CIC_BAPTISMUS,
            "Unción CIC" to CIC_UNCTIONIS,
            "Unción: Rito Ordinario" to UNCTIONIS_ORDINARIUM,
            "Viático fuera de la Misa: Sacerdote" to EUCHARISTIA_VIATICUM_SACERDOS,
            "Viático fuera de la Misa: Ministro" to EUCHARISTIA_VIATICUM_ALTER,
            "Comunión: Rito breve-Ministro" to EUCHARISTIA_BREVIS_ALTER,
            "Comunión: Rito ordinario-Ministro" to EUCHARISTIA_ORDINARIUM_ALTER,
            "Comunión: Rito ordinario-Sacerdote" to EUCHARISTIA_VIATICUM_SACERDOS,
            "Unción: Peligro de muerte" to FILE_UNCTIONIS_ARTICULO_MORTIS,
            "Unción: En duda de muerte" to FILE_UNCTIONIS_IN_DUBIO,
            "Unción sin Viático" to FILE_UNCTIONIS_SINE_VIATICUM,
            "Encomendación del Alma a Dios" to FILE_COMMENDATIONE_MORIENTIUM,
            "Comunión: con Celebración de Palabra" to EUCHARISTIA_VERBUM_EXTENSA,
            "Comunión: con Celebración de Palabra breve" to EUCHARISTIA_VERBUM_BREVIS,
            "Ángelus" to FILE_ANGELUS,
            "Regina Coeli" to FILE_REGINA,
            "Letanías" to FILE_LITANIES,
            "Comunión: Rito ordinario-Sacerdote" to EUCHARISTIA_ORDINARIUM_SACERDOS,

        )

        fun fileByMenu(menu: String): String? {
            return fileMap[menu]
        }
    }
}