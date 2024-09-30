package org.deiverbum.app

import java.io.InputStream

//TODO: Reubicar
fun interface AssetManager {
    fun open(fileName: String): InputStream
}
