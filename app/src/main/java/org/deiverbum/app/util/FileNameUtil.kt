package org.deiverbum.app.util

import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS
import org.deiverbum.app.util.Constants.FILE_VIATICUM
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
            "Viático fuera de la Misa" to FILE_VIATICUM



        )

        fun fileByMenu(menu: String): String? {
            return fileMap[menu]
        }
    }
}