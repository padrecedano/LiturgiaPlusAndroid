package org.deiverbum.app.util

import org.deiverbum.app.util.Constants.FILE_ABOUT
import org.deiverbum.app.util.Constants.FILE_AUTHOR
import org.deiverbum.app.util.Constants.FILE_HELP
import org.deiverbum.app.util.Constants.FILE_NEW
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.FILE_THANKS

class FileNameUtil {
    companion object {
        val fileMap = hashMapOf(
            "Sobre Liturgia+" to FILE_ABOUT,
            "Autor" to FILE_AUTHOR,
            "Ayuda" to FILE_HELP,
            "Agradecimientos" to FILE_THANKS,
            "Novedades" to FILE_NEW,
            "Política de Privacidad" to FILE_PRIVACY,
            "Términos y Condiciones" to FILE_TERMS
        )

        fun fileByMenu(menu: String): String? {
            return fileMap[menu]
        }
    }
}