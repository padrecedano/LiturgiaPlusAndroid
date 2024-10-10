package org.deiverbum.app.util

class FileNameUtil {
    companion object {
        val fileMap = hashMapOf(
            "Sobre Liturgia+" to "raw/menu/about_202201.json",
            "Autor" to "raw/menu/author_202201.json",
            "Ayuda" to "raw/menu/help_202201.json",
            "Agradecimientos" to "raw/menu/thanks_202201.json",
            "Novedades" to "raw/menu/new_202301.json",
        )

        fun fileByMenu(menu: String): String? {
            return fileMap[menu]
        }
    }
}